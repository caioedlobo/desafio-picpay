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
                .baseUrl("http://localhost:8081/api/mock")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
    public void receiveNotification(Transaction transaction){
        LOGGER.info("notifying transaction {}...", transaction);
        ResponseEntity<Authorization> response = null;
        try {
            response = restClient.get()
                    .retrieve()
                    .toEntity(Authorization.class);

            extracted(response);
        }
        catch(ResourceAccessException exception){
            throw new GenericException("Error while consuming notification API");
        }
    }

    private static void extracted(ResponseEntity<Authorization> response) {
        if(response.getStatusCode().isError() || !response.getBody().isSmsAuthorized()){
            throw new AuthorizationException("Notification");
        }
    }

}
