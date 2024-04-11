package com.payments.payments.repository;

import com.payments.payments.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findByIban(String iban);
}
