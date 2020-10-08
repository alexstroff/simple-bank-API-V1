package com.bank.model.to;

import com.bank.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountTO {
    private int id;
    private String number;
    private BigDecimal amount;
    private String currency;
}
