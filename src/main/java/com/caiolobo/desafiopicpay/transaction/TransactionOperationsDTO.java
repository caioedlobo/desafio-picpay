package com.caiolobo.desafiopicpay.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionOperationsDTO(
        @NotNull
        Long user,
        @Min(value = 0, message = "Valor inválido")
        BigDecimal value) {
}
