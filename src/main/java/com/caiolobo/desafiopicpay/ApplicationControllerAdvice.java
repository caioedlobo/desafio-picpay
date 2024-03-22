package com.caiolobo.desafiopicpay;

import com.caiolobo.desafiopicpay.exceptions.UsuarioJaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UsuarioJaExisteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors usuarioJaExisteException(UsuarioJaExisteException exception){
        return new ApiErrors(exception.getMessage());
    }
}
