package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.AccountRepositoryImpl;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class AccountServiceImpl implements AccountInterface {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private TxManager txManager;


    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
        this.clientRepository = new ClientRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }


    @Override
    public List<Account> getAll(int id) {
        log.trace("id={}", id);
        List<Account> allClientAccounts = null;
        try {
            allClientAccounts = accountRepository.getAllClientAccounts(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", allClientAccounts);

        return allClientAccounts;
    }

    @Override
    public Account getById(int id) {
        log.trace("id={}", id);
        Account account = null;
        try {
            account = accountRepository.getAccountById(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", account);
        return account;
    }


    @Override
    public Account getById(int clientId, int id) {
        log.trace("id={}", id);

        Account account = new Account();
        try {
            List<Account> accounts = accountRepository.getAllClientAccounts(clientId);
            for (Account a : accounts) {
                if (a.getId() == id) {
                    account = a;
                    break;
                }
            }
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", account);
        return account;
    }


    @Override
    public Account add(int clientId, Account account) {
        log.trace("got={}", account);
        Account account1 = new Account();
        try {
            account1 = accountRepository.addAccount(clientId, account);
        } catch (SQLException e) {
            log.warn("Account was not created!", e);
        }
        log.trace("returning={}", account1);

        return account1;
    }


    @Override
    public Account update(int clientId, Account account) {
        log.trace("got={}", account);
        try {
            txManager.doInTransaction(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    List<Account> accounts = accountRepository.getAllClientAccounts(clientId);
                    Account account1 = new Account();
                    for (Account a : accounts) {
                        if (a.getId() == account.getId()) {
                            accountRepository.updateAccount(account);
                            account1 = a;
                            break;
                        }
                    }
                    return account1;
                }
            });
        } catch (SQLException e) {
            log.warn("Account was not updated!", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.trace("returning={}", account);
        return account;
    }

    @Override
    public boolean delete(int clientId, int id) {
        log.trace("got={}", id);

        boolean success = false;
        try {
            boolean flag = (boolean)txManager.doInTransaction(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    List<Account> allClientAccounts = accountRepository.getAllClientAccounts(clientId);
                    for (Account a: allClientAccounts){
                        if (a.getId() == id){
                            accountRepository.deletAccount(id);
                            return true;
                        }
                    }
                    return false;
                }
            });
            if (flag) {
                success = true;
            }
        } catch (Exception e) {
            log.warn("Account was not deleted!", e);
        }
        log.trace("returning={}", success);
        return success;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
