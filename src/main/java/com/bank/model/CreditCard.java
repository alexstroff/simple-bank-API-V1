package com.bank.model;

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
    private Integer id;
    private Account account;
    private String number;
    private Date registered = new Date();
    private Client client;

}
