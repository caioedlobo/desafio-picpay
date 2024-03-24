package com.caiolobo.desafiopicpay.Transaction;

import com.caiolobo.desafiopicpay.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UsuarioService usuarioService;

    public TransactionService(TransactionRepository transactionRepository, UsuarioService usuarioService) {
        this.transactionRepository = transactionRepository;
        this.usuarioService = usuarioService;
    }

    public void transfer(Transaction transaction){
        usuarioService.withdraw(transaction.payer(), transaction.value());
        usuarioService.deposit(transaction.payee(), transaction.value());
    }
}
