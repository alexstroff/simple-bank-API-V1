package com.bank.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private Integer id;
    private Client client;
    private String number;
    private BigDecimal amount;
    private String currency;

}
