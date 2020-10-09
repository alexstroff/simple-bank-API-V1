package com.bank.model.utils;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;
import com.bank.model.to.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EntityUtils {
    public static Client fromClientToToClient(ClientTo clientTo) {
        return Client.builder().name(clientTo.getName()).email(clientTo.getEmail()).build();
    }

    public static Client fromClientToWithIdToClient(ClientToWithId clientTo) {
        return Client.builder().id(clientTo.getId()).name(clientTo.getName()).email(clientTo.getEmail()).build();
    }

    public static Account fromAccountTOToAccount(AccountTo to) {
        return Account.builder().number(to.getNumber()).amount(to.getAmount()).currency(to.getCurrency()).build();
    }

    public static Account fromAccountToWithIdToAccount(AccountToWithId to) {
        return Account.builder().id(to.getId()).number(to.getNumber()).amount(to.getAmount())
                .currency(to.getCurrency()).build();
    }

    public static CreditCard fromCreditCardTOToCreditCard(CreditCardTo to) {
        return CreditCard.builder().account(to.getAccount()).number(to.getNumber()).build();
    }

    public static CreditCard fromCreditCardToWithIdToCreditCard(CreditCardToWithId to) {
        return CreditCard.builder().id(to.getId()).account(to.getAccount()).number(to.getNumber()).build();
    }
//    public static AccountTo fromAccountToAccountTO(Account a) {
//        return AccountTo.builder().id(a.getId()).number(a.getNumber()).amount(a.getAmount()).currency(a.getCurrency()).build();
//    }
//
//    public static List<AccountTo> fromAccountToAccountTO(List<Account> accounts) {
//        List<AccountTo> accountsTO = new ArrayList<>();
//        for(Account a: accounts){
//            AccountTo accountTO = fromAccountToAccountTO(a);
//            accountsTO.add(accountTO);
//        }
//        return accountsTO;
//    }


}
