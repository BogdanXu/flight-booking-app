package com.payments.payments.service;

import com.payments.payments.model.Account;
import com.payments.payments.repository.AccountRepository;
import com.payments.payments.repository.PaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class AccountService {
    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;
    public Mono<Account> getAccountDetails(String iban) {

        Random random = new Random();

        // Generează aleatoriu una dintre cele două balanțe
        double balance = random.nextBoolean() ? 1200.00 : 3.00;

        // Creează un nou obiect Account cu balanța generată aleatoriu
        Account account = new Account(iban, balance);
        return  Mono.just(account);
    }
}
