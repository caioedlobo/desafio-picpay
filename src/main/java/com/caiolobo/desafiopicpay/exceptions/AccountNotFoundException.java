package com.caiolobo.desafiopicpay.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super("Conta não existe");
    }
}