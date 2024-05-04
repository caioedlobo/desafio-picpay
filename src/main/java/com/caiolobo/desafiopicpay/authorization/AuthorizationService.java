package com.caiolobo.desafiopicpay.authorization;

import com.caiolobo.desafiopicpay.exceptions.AuthorizationException;
import com.caiolobo.desafiopicpay.exceptions.GenericException;
import com.caiolobo.desafiopicpay.transaction.Transaction;
import com.caiolobo.desafiopicpay.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private final RestClient restClient;

    public AuthorizationService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .build();
    }

    public void authorize(Transaction transaction){
        LOGGER.info("Authorizing transaction: {}", transaction);
        ResponseEntity<Authorization> response = null;
        try {
             response = restClient.get()
                    .retrieve()
                    .toEntity(Authorization.class);

            if(response.getStatusCode().isError() || !response.getBody().isAuthorized()){
                throw new AuthorizationException("Transaction");
            }
            LOGGER.info("Transaction authorized...");
        }
        catch(ResourceAccessException exception){
            throw new GenericException("Error while consuming authorization API");
        }
    }
}
