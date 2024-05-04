package com.caiolobo.desafiopicpay.authorization;

public record Authorization(String message) {


    boolean isAuthorized(){
        return message().equalsIgnoreCase("Autorizado");
    }
}
