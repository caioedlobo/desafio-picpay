package com.caiolobo.desafiopicpay.transaction;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Object> transfer(@RequestBody Transaction transaction){
        transactionService.transfer(transaction);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@Valid @RequestBody TransactionOperationsDTO transactionOperationsDTO){
        transactionService.deposit(transactionOperationsDTO.user(), transactionOperationsDTO.value());
        return ResponseEntity.noContent().build();
    }
}
