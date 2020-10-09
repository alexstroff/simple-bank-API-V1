package com.bank.repository;

import com.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    /**
     * Get Account by Id.
     *
     * @param clientId
     * @param accountId
     * @return Client.
     * @throws SQLException if Account with Id not found
     */
    Account getById(int clientId, int accountId) throws SQLException;

    /**
     * Find and return all client's accounts.
     *
     * @param clientId
     * @return List<Account>
     * @throws SQLException if DB exceptions
     */
    List<Account> getAll(int clientId) throws SQLException;

    /**
     * Persist Account.
     *
     * @param account
     * @return Account.
     * @throws SQLException if Account Id not found or database did not allocate a number.
     */
    Account save(Account account) throws SQLException;


    /**
     * Delete Account by Id.
     *
     * @param clientId
     * @param accountId
     * @return true if success.
     * @throws SQLException if AccountId not found.
     */
    boolean delete(int clientId, int accountId) throws SQLException;
}
