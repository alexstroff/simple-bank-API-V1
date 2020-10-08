package com.bank.repository;

import com.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository extends BaseRepository<Account>{


    /**
     * Find and return all client's accounts
     * @param clientId
     * @return List<Account>
     * @throws SQLException
     */

    List<Account> getAll(int clientId) throws SQLException;

    /**
     * @param account update account
     */
//    Account updateAccount(Account account) throws SQLException;


}
