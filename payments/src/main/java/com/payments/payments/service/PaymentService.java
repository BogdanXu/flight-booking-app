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


//    public Mono<Boolean> verifyPayment(String bookingId) {
//        return paymentDetailRepository.findByBookingId(bookingId)
//                .flatMap(paymentDetail -> {
//                    boolean validIban = isIbanValid(paymentDetail.getClientIban()) && isIbanValid(paymentDetail.getOperatorIban());
//                    log.info("1234validiban: " + validIban);
//                    if (isIbanValid(paymentDetail.getClientIban()) && isIbanValid(paymentDetail.getOperatorIban())) {
//                        log.info("1234verifyPaymentWithIbans: !!!!" );
//                        return verifyPaymentWithIbans(paymentDetail);
//                    } else {
//                        log.info("1234else false!!!");
//                        return Mono.just(false);
//                    }
//                })
//                .switchIfEmpty(Mono.just(false));
//    }
//
//    private boolean isIbanValid(String iban) {
//        log.info("1234isIbanValid" + iban);
//        return iban != null && !iban.isEmpty();
//    }
//
//    private Mono<Boolean> verifyPaymentWithIbans(PaymentDetail paymentDetail) {
//        log.info("1234Verifying payment with IBANs for bookingId: {}", paymentDetail.toString());
//        return Mono.zip(
//                        accountService.getAccount(paymentDetail.getClientIban()).doOnNext(account -> log.info("Client account found for IBAN: {}", account.getIban())).defaultIfEmpty(new Account()),
//                        accountService.getAccount(paymentDetail.getOperatorIban()).doOnNext(account -> log.info("Operator account found for IBAN: {}", account.getIban())).defaultIfEmpty(new Account()),
//                        (clientAccount, operatorAccount) -> new Account[]{clientAccount, operatorAccount}
//                )
//                .flatMap(accounts -> {
//                    Account clientAccount = accounts[0];
//                    Account operatorAccount = accounts[1];
//
//                    // Verificăm dacă ambele conturi au fost găsite
//                    if (clientAccount.getId() == null || operatorAccount.getId() == null) {
//                        log.info("One or both accounts not found. Client IBAN: {}, Operator IBAN: {}", paymentDetail.getClientIban(), paymentDetail.getOperatorIban());
//                        return Mono.just(false);
//                    }
//
//                    boolean fonduri = clientAccount.getBalance() >= paymentDetail.getAmount();
//                    log.info("1234 suficiente?: " + fonduri );
//                    if (fonduri) {
//                        log.info("1234Sufficient funds for payment. Processing transfer for bookingId: {}", paymentDetail.getBookingId());
//                        clientAccount.setBalance(clientAccount.getBalance() - paymentDetail.getAmount());
//                        operatorAccount.setBalance(operatorAccount.getBalance() + paymentDetail.getAmount());
//
//                        return accountService.updateAccount(clientAccount)
//                                .then(accountService.updateAccount(operatorAccount))
//                                .thenReturn(true);
//                    } else {
//                        log.info("1234else pe false");
//                        return Mono.just(false);
//                    }
//                })
//                .defaultIfEmpty(false);
//    }
///////////////////////////////////////////////

////    public Mono<Boolean> verifyPayment(String bookingId) {
//        return paymentDetailRepository.findByBookingId(bookingId)
//                .log("@@@@@bookingId: " + bookingId)
//                .flatMap(paymentDetail -> {
//                    // Verificăm dacă există atât clientIban cât și operatorIban
//                    if (isIbanValid(paymentDetail.getClientIban()) && isIbanValid(paymentDetail.getOperatorIban())) {
//                        // Dacă ambele IBAN-uri sunt valide, continuăm cu verificarea plății
//                        return verifyPaymentWithIbans(paymentDetail);
//                    } else {
//                        // Dacă unul dintre IBAN-uri lipsește, returnăm false
//                        return Mono.just(false);
//                    }
//                })
//                .switchIfEmpty(Mono.just(false));
//    }
//
//    // Metoda pentru verificarea dacă IBAN-ul este valid (nu este null și nu este un șir gol)
//    private boolean isIbanValid(String iban) {
//        return iban != null && !iban.isEmpty();
//    }
//
//    // Metoda pentru verificarea plății cu IBAN-uri
//    private Mono<Boolean> verifyPaymentWithIbans(PaymentDetail paymentDetail) {
//        // Obținem detaliile contului în mod reactiv pentru clientIban
//        return accountService.getAccountDetails(paymentDetail.getClientIban())
//                .map(account -> account.getBalance() >= paymentDetail.getAmount())
//                // Dacă contul nu există, returnăm false
//                .defaultIfEmpty(false);
//    }

//    public Mono<Boolean> verifyPayment(String bookingId) {
//
//        // Folosim flatMap pentru a procesa PaymentDetail odată ce este returnat
//
//        return paymentDetailRepository.findByBookingId(bookingId)
//                .log("@@@@@bookingId: " +bookingId)
//                .flatMap(paymentDetail ->
//                        // Obținem detaliile contului în mod reactiv
//                        accountService.getAccountDetails(paymentDetail.getClientIban())
//                                .map(account -> account.getBalance() >= paymentDetail.getAmount())
//                                // Dacă contul nu există, returnăm false
//                                .defaultIfEmpty(false)
//                )
//                // Dacă nu există PaymentDetail pentru ID-ul dat, returnăm Mono.just(false)
//                .switchIfEmpty(Mono.just(false));
//    }
}
