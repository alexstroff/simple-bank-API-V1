package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"client"})
public class Account {
    private int id;
    private Client client;
    private String number;
    private BigDecimal amount;
    private String currency;

    public Account(Account account) {
        this.id = account.getId();
        this.client = account.getClient();
        this.number = account.getNumber();
        this.amount = account.getAmount();
        this.currency = account.getCurrency();
    }
}
