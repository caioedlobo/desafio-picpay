package com.caiolobo.desafiopicpay.account;

import com.caiolobo.desafiopicpay.exceptions.AccountNotFoundException;
import com.caiolobo.desafiopicpay.exceptions.InsufficientFundsException;
import com.caiolobo.desafiopicpay.exceptions.UsuarioJaExisteException;
import com.caiolobo.desafiopicpay.exceptions.ValidateException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account criaUsuario(CreateUsuarioDTO novoUsuario){
        if(accountRepository.userExists(novoUsuario.email())){
            throw new UsuarioJaExisteException();
        }
        return accountRepository.save(new Account(novoUsuario));
    }

    public Account searchAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }


}
