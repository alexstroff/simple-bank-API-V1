package com.bank.service;

import com.bank.model.Account;

import java.util.List;

public interface AccountInterface extends BaseService<Account> {

    List<Account> getAll(int id);

    Account add(int clientId, Account account);
}
