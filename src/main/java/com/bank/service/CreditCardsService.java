package com.bank.service;

import com.bank.model.CreditCard;

import java.util.List;

public interface CreditCardsService extends BaseService<CreditCard> {

    List<CreditCard> getAll(int id);

//    CreditCard add(int accountId, CreditCard card);
//
//    CreditCard update(CreditCard card);
}
