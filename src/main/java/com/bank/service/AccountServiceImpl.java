package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.repository.AccountRepository;
import com.bank.repository.AccountRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private TxManager txManager;


    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public Account getById(int id) {
        log.trace("id={}", id);
        Account account = null;
        try {
            account = accountRepository.getById(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", account);
        return account;
    }



    @Override
    public List<Account> getAll(int id) {
        log.trace("id={}", id);
        List<Account> allClientAccounts = null;
        try {
            allClientAccounts = accountRepository.getAll(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", allClientAccounts);

        return allClientAccounts;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Account add(int clientId, Account account) {
        log.trace("got={}", account);
        Account account1 = new Account();
        try {
            account.setClient(Client.builder().id(clientId).build());
            account1 = accountRepository.save(account);
//            account1 = accountRepository.addAccount(clientId, account);
        } catch (SQLException e) {
            log.warn("Account was not created!", e);
        }
        log.trace("returning={}", account1);

        return account1;
    }


    @Override
    public Account update(Account account) {
        log.trace("got={}", account);

        try {
            accountRepository.save(account);
        } catch (SQLException e) {
            log.warn("Account was not updated!", e);
        }
        log.trace("returning={}", account);
        return account;
    }

    @Override
    public boolean delete(int id) {
        log.trace("got={}", id);

        boolean success = false;
        try {
            if (txManager.doInTransaction(() -> accountRepository.delete(id))) {
                success = true;
            }
        } catch (Exception e) {
            log.warn("Account was not deleted!", e);
        }
        log.trace("returning={}", success);
        return success;
    }
}
