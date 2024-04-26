package com.caiolobo.desafiopicpay.exceptions;

public class AuthorizationException extends RuntimeException{


    public AuthorizationException(String message) {
        super(message + " not authorized");
    }
}
