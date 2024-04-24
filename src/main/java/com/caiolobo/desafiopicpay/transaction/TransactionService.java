package com.caiolobo.desafiopicpay.transaction;

import com.caiolobo.desafiopicpay.account.AccountType;
import com.caiolobo.desafiopicpay.account.Account;
import com.caiolobo.desafiopicpay.account.AccountService;
import com.caiolobo.desafiopicpay.authorization.AuthorizationService;
import com.caiolobo.desafiopicpay.exceptions.InsufficientFundsException;
import com.caiolobo.desafiopicpay.exceptions.ValidateException;
import com.caiolobo.desafiopicpay.notification.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, AuthorizationService authorizationService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void transfer(Transaction transaction){
        validateTransaction(transaction);
        transactionRepository.save(transaction);
        withdraw(transaction.getPayer(), transaction.getValue());
        deposit(transaction.getPayee(), transaction.getValue());

        authorizationService.authorize(transaction);
        notificationService.notify(transaction);

    }

    public void withdraw(Long user, BigDecimal value){
        Account account = accountService.searchAccount(user);
        validateBalance(account.getBalance(), value);
        transactionRepository.withdraw(user, value);
    }

    public void deposit(Long user, BigDecimal value){
        accountService.searchAccount(user);
        transactionRepository.deposit(user, value);
    }

    private void validateTransaction(Transaction transaction){
        LOGGER.info("Validating transaction {}", transaction);
        Account payeer = accountService.searchAccount(transaction.getPayer());
        accountService.searchAccount(transaction.getPayee());
        if(payeer.getType() == AccountType.PJ.getValue()){
            throw new ValidateException("Conta é PJ");
        }
        validateBalance(payeer.getBalance(), transaction.getValue());
    }

    private void validateBalance(BigDecimal accountBalance, BigDecimal transactionValue){
        if(accountBalance.compareTo(transactionValue) < 0){
            throw new InsufficientFundsException();
        }

    }
}
