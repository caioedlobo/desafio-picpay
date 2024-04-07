package com.caiolobo.desafiopicpay.exceptions;

import com.caiolobo.desafiopicpay.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler({UsuarioJaExisteException.class, AccountNotFoundException.class, ValidateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleNotFoundException(Exception exception){
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex ){
        List<String> errors = ex.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }

    /*@ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors handleAuthorizationException(AuthorizationException exception ){
        return new ApiErrors(exception.getMessage());
    }*/

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiErrors handleGenericException(GenericException exception ){
        return new ApiErrors(exception.getMessage());
    }

    /*@ExceptionHandler(Exception.class)
    public ApiErrors handleKafkaException(KafkaException exception ){
        return new ApiErrors(exception.getMessage());
    }*/

}
