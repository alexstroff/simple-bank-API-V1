package com.bank.service;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import java.util.List;

public interface CreditCardsInterface extends BaseService<CreditCard> {

    List<CreditCard> getAll(int id);

    CreditCard add(int accountId, CreditCard card);


}
