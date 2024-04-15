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

    public Mono<Boolean> revertTransfer(String operatorIban, String clientIban, double amount) {
        // Recuperează contul operatorului și contul clientului simultan
        Mono<Account> operatorAccountMono = accountRepository.findByIban(operatorIban);
        Mono<Account> clientAccountMono = accountRepository.findByIban(clientIban);

        return Mono.zip(operatorAccountMono, clientAccountMono)
                .flatMap(tuple -> {
                    Account operatorAccount = tuple.getT1();
                    Account clientAccount = tuple.getT2();

                    // Verifică dacă operatorul are suficiente fonduri pentru a face transferul înapoi
                    if (operatorAccount.getBalance() < amount) {
                        return Mono.just(false); // Nu are fonduri suficiente, deci operația eșuează
                    }

                    // Actualizează soldurile pentru ambele conturi
                    operatorAccount.setBalance(operatorAccount.getBalance() - amount);
                    clientAccount.setBalance(clientAccount.getBalance() + amount);

                    // Salvăm ambele conturi înapoi în baza de date
                    return accountRepository.save(operatorAccount)
                            .then(accountRepository.save(clientAccount))
                            .thenReturn(true); // Dacă totul e bine, returnăm true
                })
                .onErrorResume(e -> {
                    // Loghează orice eroare și returnează false
                    log.error("Error while reverting funds transfer", e);
                    return Mono.just(false);
                });
    }

}

