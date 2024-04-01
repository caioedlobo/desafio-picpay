package com.caiolobo.desafiopicpay.authorization;

import com.caiolobo.desafiopicpay.exceptions.AuthorizationException;
import com.caiolobo.desafiopicpay.transaction.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizationService {

    private final RestClient restClient;

    public AuthorizationService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("localhost:8081/api/mock")
                .build();
    }

    public void authorize(Transaction transaction){
        ResponseEntity<Authorization> response = restClient.get()
                .retrieve()
                .toEntity(Authorization.class);
        if(response.getStatusCode().isError() || !response.getBody().isNotificationAuthorized()){
            throw new AuthorizationException("Notification");
        }
    }
}
