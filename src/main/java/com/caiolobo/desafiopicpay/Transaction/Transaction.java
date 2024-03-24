package com.caiolobo.desafiopicpay.Transaction;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name= "transactions")
public record Transaction(
        @Id Long id,
        Long payer,
        Long payee,
        BigDecimal value,
        @CreatedDate LocalDateTime createdAt) {
    public Transaction {
        value = value.setScale(2);
    }
}
