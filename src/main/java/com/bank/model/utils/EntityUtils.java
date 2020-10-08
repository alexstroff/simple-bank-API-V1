package com.bank.model.utils;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.to.AccountTO;
import com.bank.model.to.ClientTo;
import com.bank.model.to.ClientToWithId;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {
    public static Client fromClientToToClient(ClientTo clientTo) {
        return Client.builder().name(clientTo.getName()).email(clientTo.getEmail()).build();
    }

    public static Client fromClientToWithIdToClient(ClientToWithId clientTo) {
        return Client.builder().id(clientTo.getId()).name(clientTo.getName()).email(clientTo.getEmail()).build();
    }

    public static AccountTO fromAccountToAccountTO(Account a) {
        return AccountTO.builder().id(a.getId()).number(a.getNumber()).amount(a.getAmount()).currency(a.getCurrency()).build();
    }

    public static List<AccountTO> fromAccountToAccountTO(List<Account> accounts) {
        List<AccountTO> accountsTO = new ArrayList<>();
        for(Account a: accounts){
            AccountTO accountTO = fromAccountToAccountTO(a);
            accountsTO.add(accountTO);
        }
        return accountsTO;
    }


}
