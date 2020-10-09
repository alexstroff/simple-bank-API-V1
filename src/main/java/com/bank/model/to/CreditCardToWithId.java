package com.bank.model.to;

import com.bank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardToWithId {
    private int id;
    private Account account;
    private String number;
}
