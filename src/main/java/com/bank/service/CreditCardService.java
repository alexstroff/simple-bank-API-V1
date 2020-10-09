package com.bank.service;

import com.bank.model.CreditCard;

import java.util.List;

public interface CreditCardService {

    CreditCard getById(int clientId, int accountId, int cardId);

    List<CreditCard> getAll(int clientId, int accountId);

    CreditCard save(CreditCard card);

    boolean delete(int clientId, int accountId, int cardId);

}
