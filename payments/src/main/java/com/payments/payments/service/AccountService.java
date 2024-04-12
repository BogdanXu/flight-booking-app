package com.payments.payments.service;

import com.payments.payments.model.Account;
import com.payments.payments.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    public AccountRepository accountRepository;

    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Mono<Account> getAccount(String iban) {
        log.info("Fetching account for IBAN: {}", iban);
        return accountRepository.findByIban(iban)
                .doOnSuccess(account -> {
                    if (account != null) {
                        log.info("Found account for IBAN: {}", iban);
                    } else {
                        log.warn("No account found for IBAN: {}", iban);
                    }
                })
                .doOnError(error -> log.error("Error fetching account for IBAN: " + iban, error));
    }

    public Mono<Account> updateAccount(Account account) {
        return accountRepository.save(account);
    }
//
//    public Mono<Account> getOperatorAccount(String operatorIban) {
//        // Use the operatorIban passed as a parameter, not a hard-coded value
//        return accountRepository.findByIban(operatorIban);
//    }
//
//    public Mono<Account> saveAccount(Account account) {
//        // Asigurăm că account-ul are un ID (_id în MongoDB) setat
//        if (account.getId() == null) {
//            return Mono.error(new IllegalArgumentException("Cannot update an account without an ID."));
//        }
//        // Actualizăm documentul existent în baza de date MongoDB
//        return accountRepository.save(account);
//    }


//    @Autowired
//    public AccountRepository accountRepository;

//    @Autowired
//    private PaymentDetailRepository paymentDetailRepository;
//    public Mono<Account> getAccountDetails(String iban) {
//
//        Random random = new Random();
//
//        // Generează aleatoriu una dintre cele două balanțe
//        double balance = random.nextBoolean() ? 1200.00 : 3.00;
//
//        // Creează un nou obiect Account cu balanța generată aleatoriu
//        Account accountClient = new Account(iban, balance);
//
//
//        return  Mono.just(accountClient);
//    }




}

