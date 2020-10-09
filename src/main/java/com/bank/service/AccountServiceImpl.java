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

//    @Override
//    public Account getById(int id) {
//        log.trace("id={}", id);
//        Account account = null;
//        try {
//            account = txManager.doInTransaction(() -> repository.getById(id));
//        } catch (Exception e) {
//            log.warn("id={}", id, e);
//        }
//        log.trace("returning={}", account);
//        return account;
//    }

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

//    @Override
//    public boolean delete(int id) {
//        boolean success = false;
//        try {
//            success = txManager.doInTransaction(() -> repository.delete(id));
//        } catch (Exception e) {
//            log.warn("Account was not updated!", e);
//        }
//        log.trace("returning={}", success);
//        return success;
//    }

    @Override
//    public boolean delete(int clientId, int id) {
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

//    @Override
//    public Account add(int clientId, Account account) {
//        return null;
//    }
//
//    @Override
//    public Account update(int cId, Account a) {
//        return null;
//    }

//    @Override
//    public Account save(Account account) {
//        return null;
//    }




}









//    @Override
//    public Account getById(int clientId, int id) {
//        log.trace("id={}", id);
//
//        Account account = new Account();
//        try {
//            List<Account> accounts = accountRepository.getAll(clientId);
//            for (Account a : accounts) {
//                if (a.getId() == id) {
//                    account = a;
//                    break;
//                }
//            }
//        } catch (SQLException e) {
//            log.warn("id={}", id, e);
//        }
//        log.trace("returning={}", account);
//        return account;
//    }
//
//
//    @Override
//    public Account add(int clientId, Account account) {
//        log.trace("got={}", account);
//        Account account1 = new Account();
//        try {
//            account1 = accountRepository.(clientId, account);
//        } catch (SQLException e) {
//            log.warn("Account was not created!", e);
//        }
//        log.trace("returning={}", account1);
//
//        return account1;
//    }
//
//
//    @Override
//    public Account update(int clientId, Account account) {
//        log.trace("got={}", account);
//        try {
//            txManager.doInTransaction(new Callable<Object>() {
//                @Override
//                public Object call() throws Exception {
//                    List<Account> accounts = accountRepository.getAllClientAccounts(clientId);
//                    Account account1 = new Account();
//                    for (Account a : accounts) {
//                        if (a.getId() == account.getId()) {
//                            accountRepository.updateAccount(account);
//                            account1 = a;
//                            break;
//                        }
//                    }
//                    return account1;
//                }
//            });
//        } catch (SQLException e) {
//            log.warn("Account was not updated!", e);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.trace("returning={}", account);
//        return account;
//    }
//
//    @Override
//    public boolean delete(int clientId, int id) {
//        log.trace("got={}", id);
//
//        boolean success = false;
//        try {
//            boolean flag = (boolean)txManager.doInTransaction(new Callable<Object>() {
//                @Override
//                public Object call() throws Exception {
//                    List<Account> allClientAccounts = accountRepository.getAllClientAccounts(clientId);
//                    for (Account a: allClientAccounts){
//                        if (a.getId() == id){
//                            accountRepository.deletAccount(id);
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
//            if (flag) {
//                success = true;
//            }
//        } catch (Exception e) {
//            log.warn("Account was not deleted!", e);
//        }
//        log.trace("returning={}", success);
//        return success;
//    }
//
//    @Override
//    public Account save(Account account) {
//        return null;
//    }
//
//    @Override
//    public boolean delete(int id) {
//        return false;
//    }
//}

//=================



//package com.bank.service;
//
//import com.bank.model.Account;
//import com.bank.model.Client;
//import com.bank.repository.AccountRepository;
//import com.bank.repository.AccountRepositoryImpl;
//import com.bank.repository.txManager.TxManager;
//import com.bank.repository.txManager.TxManagerImpl;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.SQLException;
//import java.util.List;
//
//@Slf4j
//public class AccountServiceImpl implements AccountService {
//
//    private final AccountRepository accountRepository;
//    private TxManager txManager;
//
//
//    public AccountServiceImpl() {
//        this.accountRepository = new AccountRepositoryImpl();
//        this.txManager = new TxManagerImpl();
//    }
//
//    @Override
//    public Account getById(int id) {
//        log.trace("id={}", id);
//        Account account = null;
//        try {
//            account = accountRepository.getById(id);
//        } catch (SQLException e) {
//            log.warn("id={}", id, e);
//        }
//        log.trace("returning={}", account);
//        return account;
//    }
//
//    @Override
//    public List<Account> getAll(int id) {
//        log.trace("id={}", id);
//        List<Account> allClientAccounts = null;
//        try {
//            allClientAccounts = accountRepository.getAll(id);
//        } catch (SQLException e) {
//            log.warn("id={}", id, e);
//        }
//        log.trace("returning={}", allClientAccounts);
//
//        return allClientAccounts;
//    }
//
//    @Override
//    public Account save(Account account) {
//        return null;
//    }
//
//    @Override
//    public Account add(int clientId, Account account) {
//        log.trace("got={}", account);
//        Account account1 = new Account();
//        try {
//            account.setClient(Client.builder().id(clientId).build());
//            account1 = accountRepository.save(account);
////            account1 = accountRepository.addAccount(clientId, account);
//        } catch (SQLException e) {
//            log.warn("Account was not created!", e);
//        }
//        log.trace("returning={}", account1);
//
//        return account1;
//    }
//
//
//    @Override
//    public Account update(Account account) {
//        log.trace("got={}", account);
//
//        try {
//            accountRepository.save(account);
//        } catch (SQLException e) {
//            log.warn("Account was not updated!", e);
//        }
//        log.trace("returning={}", account);
//        return account;
//    }
//
//    @Override
//    public boolean delete(int id) {
//        log.trace("got={}", id);
//
//        boolean success = false;
//        try {
//            if (txManager.doInTransaction(() -> accountRepository.delete(id))) {
//                success = true;
//            }
//        } catch (Exception e) {
//            log.warn("Account was not deleted!", e);
//        }
//        log.trace("returning={}", success);
//        return success;
//    }
//}
