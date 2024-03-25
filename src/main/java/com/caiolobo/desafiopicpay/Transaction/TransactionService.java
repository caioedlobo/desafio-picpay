package com.caiolobo.desafiopicpay.Transaction;

import com.caiolobo.desafiopicpay.AccountType;
import com.caiolobo.desafiopicpay.Usuario;
import com.caiolobo.desafiopicpay.UsuarioService;
import com.caiolobo.desafiopicpay.exceptions.ValidateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UsuarioService usuarioService;

    public TransactionService(TransactionRepository transactionRepository, UsuarioService usuarioService) {
        this.transactionRepository = transactionRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public void transfer(Transaction transaction){
        validate(transaction);
        transactionRepository.save(transaction);
        usuarioService.withdraw(transaction.getPayer(), transaction.getValue());
        usuarioService.deposit(transaction.getPayee(), transaction.getValue());

    }

    private void validate(Transaction transaction){
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
