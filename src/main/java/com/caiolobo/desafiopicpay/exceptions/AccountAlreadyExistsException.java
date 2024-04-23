package com.caiolobo.desafiopicpay.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException() {
        super("Usuário já existe");
    }

}
