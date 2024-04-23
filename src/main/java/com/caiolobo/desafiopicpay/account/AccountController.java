package com.caiolobo.desafiopicpay.account;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody CreateAccountDTO newAccount){
        return ResponseEntity.ok(accountService.createAccount(newAccount));
    }
}
