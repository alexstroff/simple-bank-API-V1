package com.bank.service;

import com.bank.model.Account;

import java.util.List;

public interface AccountService {

    Account getById(int parentId, int entityId);

    List<Account> getAll(int id);

    Account save(Account account);

    boolean delete(int cId, int aId);
}
