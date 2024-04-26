package com.caiolobo.desafiopicpay.notification;

public class NotificationException extends RuntimeException{

    public NotificationException(String message) {
        super("Notification not authorized");
    }
}
