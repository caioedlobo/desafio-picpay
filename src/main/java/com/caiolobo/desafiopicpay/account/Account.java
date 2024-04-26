package com.caiolobo.desafiopicpay.account;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Table(name = "accounts")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String document;
    private String email;
    private String password;
    private BigDecimal balance;
    private int type;

    public Account() {
    }

    public Account(CreateAccountDTO newAccount) {
        this.name = newAccount.name();
        this.document = newAccount.document();
        this.email = newAccount.email();
        this.password = newAccount.password();
        this.balance = new BigDecimal("0.00");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }
}
