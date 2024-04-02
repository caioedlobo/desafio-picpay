package com.caiolobo.desafiopicpay.transaction;

import com.caiolobo.desafiopicpay.AccountType;
import com.caiolobo.desafiopicpay.Usuario;
import com.caiolobo.desafiopicpay.UsuarioService;
import com.caiolobo.desafiopicpay.authorization.AuthorizationService;
import com.caiolobo.desafiopicpay.exceptions.ValidateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final UsuarioService usuarioService;
    private final AuthorizationService authorizationService;

    public TransactionService(TransactionRepository transactionRepository, UsuarioService usuarioService, AuthorizationService authorizationService) {
        this.transactionRepository = transactionRepository;
        this.usuarioService = usuarioService;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void transfer(Transaction transaction){
        validate(transaction);
        transactionRepository.save(transaction);
        usuarioService.withdraw(transaction.getPayer(), transaction.getValue());
        usuarioService.deposit(transaction.getPayee(), transaction.getValue());

        authorizationService.authorize(transaction);

    }

    private void validate(Transaction transaction){
        LOGGER.info("Validating transaction {}", transaction);
        Usuario payeer = usuarioService.searchAccount(transaction.getPayer());
        usuarioService.searchAccount(transaction.getPayee());
        if(payeer.getType() == AccountType.PJ.getValue()){
            throw new ValidateException("Conta é PJ");
        }
        if(payeer.getSaldo().compareTo(transaction.getValue()) < 0){
            throw new ValidateException("Saldo insuficiente para realizar transferência");
        }


    }
}
