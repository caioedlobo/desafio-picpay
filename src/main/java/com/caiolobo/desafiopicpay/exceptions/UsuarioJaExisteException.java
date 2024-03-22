package com.caiolobo.desafiopicpay.exceptions;

public class UsuarioJaExisteException extends RuntimeException{
    public UsuarioJaExisteException() {
        super("Usuário já existe");
    }

}
