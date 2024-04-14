package com.caiolobo.desafiopicpay.account;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> criarUsuario(@Valid @RequestBody CreateUsuarioDTO novoUsuario){
        return ResponseEntity.ok(accountService.criaUsuario(novoUsuario));
    }
}
