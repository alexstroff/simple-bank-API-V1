package com.bank.model;

import com.bank.model.to.ClientTo;
import com.bank.model.to.ClientToWithId;

public class EntityUtils {
    public static Client fromClientToToClient(ClientTo clientTo) {
        return Client.builder().name(clientTo.getName()).email(clientTo.getEmail()).build();
    }

    public static Client fromClientToWithIdToClient(ClientToWithId clientTo) {
        return Client.builder().id(clientTo.getId()).name(clientTo.getName()).email(clientTo.getEmail()).build();
    }
}
