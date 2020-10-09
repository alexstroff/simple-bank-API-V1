package com.bank.service;

import com.bank.model.Account;

import java.util.List;

public interface AccountService extends BaseService<Account> {

    Account getById(int parentId, int entityId);

    List<Account> getAll(int id);

    Account save(Account account);

//    Account add(int clientId, Account account);
//
//    Account update(int cId, Account a);

//    boolean delete(int id);

    boolean delete(int cId, int aId);
}
