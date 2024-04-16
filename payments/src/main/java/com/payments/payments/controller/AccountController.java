package com.payments.payments.controller;

import com.payments.payments.model.Account;
import com.payments.payments.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public Mono<Account> createAccount(@RequestBody Account account) {
       return accountService.createAccount(account);

    }

}
