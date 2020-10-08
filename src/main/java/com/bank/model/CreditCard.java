package com.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCard {
    private int id;
    private Account account;
    private String number;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date registered = new Date();
    private Client client;

    public CreditCard(CreditCard card) {
        this.id = card.getId();
        this.account = card.getAccount();
        this.number = card.getNumber();
        this.registered = card.getRegistered();
        this.client = card.client;
    }
}
