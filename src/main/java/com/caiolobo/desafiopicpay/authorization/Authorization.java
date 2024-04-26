package com.caiolobo.desafiopicpay.authorization;

public record Authorization(Long id, ResponseAuthorization response) {


    public boolean isNotificationAuthorized(){
        return response().notification();
    }
    public boolean isSmsAuthorized(){
        return response().sms();
    }
}
