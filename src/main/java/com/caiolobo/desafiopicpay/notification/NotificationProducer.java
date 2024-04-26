package com.caiolobo.desafiopicpay.notification;

import com.caiolobo.desafiopicpay.transaction.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class NotificationProducer {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Transaction transaction){
        kafkaTemplate.send("transaction-notification", transaction);
    }
}
