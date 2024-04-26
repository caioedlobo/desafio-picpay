package com.caiolobo.desafiopicpay.account;

public enum AccountType {
    PF(0), PJ(1);
    private int value;
    AccountType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
