package com.bank.service;

import com.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {

    /**
     * Get CreditCard by Id.
     *
     * @param clientId
     * @param accountId
     * @return CreditCard.
     */
    Account getById(int clientId, int accountId);

    /**
     * Find and return all client's accounts by Id.
     *
     * @param id
     * @return List<Account>
     */
    List<Account> getAll(int id);

    /**
     * Persist Account.
     *
     * @param account
     * @return Account.
     */
    Account save(Account account);

    /**
     * Delete Account by Id.
     *
     * @param clientId
     * @param accountId
     * @return true if success.
     */
    boolean delete(int clientId, int accountId);
}
