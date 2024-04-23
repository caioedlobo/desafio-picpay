package com.caiolobo.desafiopicpay.account;

import com.caiolobo.desafiopicpay.exceptions.AccountNotFoundException;
import com.caiolobo.desafiopicpay.exceptions.AccountAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(CreateAccountDTO newAccount){
        if(accountRepository.userExists(newAccount.email())){
            throw new AccountAlreadyExistsException();
        }
        return accountRepository.save(new Account(newAccount));
    }

    public Account searchAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }


}
