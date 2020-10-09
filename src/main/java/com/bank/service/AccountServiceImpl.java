package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.AccountRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private TxManager txManager;

    public AccountServiceImpl() {
        this.repository = new AccountRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public Account getById(int parentId, int entityId) {
        log.trace("parentId={}, entityId={}", parentId, entityId);
        Account account = null;
        try {
            account = txManager.doInTransaction(() -> repository.getById(parentId, entityId));
        } catch (Exception e) {
            log.warn("parentId={}, entityId={}", parentId, entityId, e);
        }
        log.trace("returning={}", account);
        return account;
    }

    @Override
    public List<Account> getAll(int id) {
        log.trace("id={}", id);
        List<Account> allClientAccounts = null;
        try {
            allClientAccounts = repository.getAll(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", allClientAccounts);

        return allClientAccounts;
    }


    @Override
    public Account save(Account account) {
        log.trace("got parentId={}, account=", account);
        try {
            Account finalClient = account;
            account = txManager.doInTransaction(() -> repository.save(finalClient));
        } catch (Exception e) {
            log.warn("Account was not updated!", e);
        }
        log.trace("returning={}", account);
        return account;
    }

    @Override
    public boolean delete(int parentId, int entityId) {
        log.trace("got parentId={}, entityId={}", parentId, entityId);

        boolean success = false;
        try {
            success = txManager.doInTransaction(() -> repository.delete(parentId, entityId));
        } catch (Exception e) {
            log.warn("Account was not updated!", e);
        }
        log.trace("returning={}", success);
        return success;
    }
}
