package com.payments.payments.service;


import com.payments.payments.kafka.consumer.PaymentUpdateListener;
import com.payments.payments.model.Account;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private AccountService accountService;


    public Mono<Boolean> verifyPayment(PaymentDetail paymentDetail) {
        log.info("Verifying payment directly with paymentDetail object: {}", paymentDetail);

        if (!isIbanValid(paymentDetail.getClientIban()) || !isIbanValid(paymentDetail.getOperatorIban())) {
            log.info("Invalid IBANs, cannot proceed with payment verification.");
            return Mono.just(false);
        }

        return verifyPaymentWithIbans(paymentDetail);
    }

    private Mono<Boolean> verifyPaymentWithIbans(PaymentDetail paymentDetail) {
        log.info("Verifying payment with IBANs for bookingId: {}", paymentDetail.getBookingId());

        return Mono.zip(
                        accountService.getAccount(paymentDetail.getClientIban()),
                        accountService.getAccount(paymentDetail.getOperatorIban())
                )
                .flatMap(accounts -> {
                    Account clientAccount = accounts.getT1();
                    Account operatorAccount = accounts.getT2();

                    if (clientAccount == null || operatorAccount == null) {
                        log.warn("One or both accounts not found. Client IBAN: {}, Operator IBAN: {}",
                                paymentDetail.getClientIban(), paymentDetail.getOperatorIban());
                        return Mono.just(false);
                    }

                    if (clientAccount.getBalance() < paymentDetail.getAmount()) {
                        log.info("Insufficient funds in client account.");
                        return Mono.just(false);
                    }

                    log.info("Sufficient funds for payment. Processing transfer for bookingId: {}", paymentDetail.getBookingId());
                    clientAccount.setBalance(clientAccount.getBalance() - paymentDetail.getAmount());
                    operatorAccount.setBalance(operatorAccount.getBalance() + paymentDetail.getAmount());

                    return accountService.updateAccount(clientAccount)
                            .then(accountService.updateAccount(operatorAccount))
                            .thenReturn(true);
                })
                .defaultIfEmpty(false);
    }

    private boolean isIbanValid(String iban) {
        return iban != null && !iban.isEmpty();
    }


    public Mono<Void> revertPayment(String bookingId) {
        return paymentDetailRepository.findByBookingId(bookingId)
                .switchIfEmpty(Mono.error(new IllegalStateException("No payment detail found for bookingId: " + bookingId)))
                .flatMap(paymentDetail -> {
                    if (!"ACCEPTED".equals(paymentDetail.getStatus())) {
                        return Mono.error(new IllegalStateException("Payment status is not ACCEPTED for bookingId: " + bookingId));
                    }

                    return Mono.zip(
                            accountService.getAccount(paymentDetail.getOperatorIban()),
                            accountService.getAccount(paymentDetail.getClientIban())
                    ).flatMap(tuple -> {
                        Account operatorAccount = tuple.getT1();
                        Account clientAccount = tuple.getT2();

                        if (operatorAccount == null || clientAccount == null) {
                            return Mono.error(new IllegalStateException("One or both accounts could not be found"));
                        }

                        if (operatorAccount.getBalance() < paymentDetail.getAmount()) {
                            return Mono.error(new IllegalStateException("Operator account has insufficient funds for bookingId: " + bookingId));
                        }

                        operatorAccount.setBalance(operatorAccount.getBalance() - paymentDetail.getAmount());
                        clientAccount.setBalance(clientAccount.getBalance() + paymentDetail.getAmount());

                        return Mono.when(
                                accountService.updateAccount(operatorAccount),
                                accountService.updateAccount(clientAccount)
                        );
                    }).then(Mono.fromRunnable(() -> {
                        paymentDetail.setStatus("REVERTED");
                        paymentDetailRepository.save(paymentDetail).subscribe();
                        log.info("Payment status set to REVERTED for bookingId: {}", bookingId);
                    }));
                })
                .doOnSuccess(aVoid -> log.info("Revert payment process completed successfully for bookingId: {}", bookingId))
                .doOnError(e -> log.error("An error occurred during the revert payment process for bookingId: {}", bookingId, e)).then();
    }

}
