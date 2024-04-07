package com.caiolobo.desafiopicpay.exceptions;

import java.util.Collections;
import java.util.List;

public class ApiErrors {
    List<String> errors;

    public ApiErrors(String message) {
        this.errors = Collections.singletonList(message);
    }
    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
