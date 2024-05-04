package com.caiolobo.desafiopicpay.notification;

import com.caiolobo.desafiopicpay.authorization.Authorization;
import com.caiolobo.desafiopicpay.exceptions.AuthorizationException;
import com.caiolobo.desafiopicpay.exceptions.GenericException;
import com.caiolobo.desafiopicpay.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Service
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
    private final RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder){
        restClient = builder
                .baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
    public void receiveNotification(Transaction transaction){
        LOGGER.info("notifying transaction {}...", transaction);
        ResponseEntity<Notification> response = null;
        try {
            response = restClient.get()
                    .retrieve()
                    .toEntity(Notification.class);

            if(response.getStatusCode().isError() || !response.getBody().isServiceAvailable()){
                throw new AuthorizationException("Notification");
            }
            LOGGER.info("notification has been sent {}...", response.getBody());
        }
        catch(ResourceAccessException exception){
            throw new GenericException("Error while consuming notification API");
        }
    }
}
