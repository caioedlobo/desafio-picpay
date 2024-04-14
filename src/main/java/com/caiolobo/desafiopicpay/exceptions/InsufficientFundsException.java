package com.caiolobo.desafiopicpay.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Saldo insuficiente para realizar transferência");
    }
}
