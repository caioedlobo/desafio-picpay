package com.caiolobo.desafiopicpay.notification;

public record Notification (Boolean message){

    Boolean isServiceAvailable(){
        return message();
    }
}
