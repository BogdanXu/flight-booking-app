package com.payments.payments.service;


import com.payments.payments.repository.PaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private AccountService accountService;


    public Mono<Boolean> verifyPayment(String bookingId) {



        // Folosim flatMap pentru a procesa PaymentDetail odată ce este returnat

        return paymentDetailRepository.findByBookingId(bookingId)
                .log("@@@@@z" +bookingId)
                .flatMap(paymentDetail ->
                        // Obținem detaliile contului în mod reactiv
                        accountService.getAccountDetails(paymentDetail.getIban())
                                .map(account -> account.getBalance() >= paymentDetail.getAmount())


                                // Dacă contul nu există, returnăm false
                                .defaultIfEmpty(false)
                )
                // Dacă nu există PaymentDetail pentru ID-ul dat, returnăm Mono.just(false)
                .switchIfEmpty(Mono.just(false));
    }
}
