package com.bank.repository;

import com.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository extends BaseRepository<Account> {

    Account getById(int parentId, int entityId) throws SQLException;

    /**
     * Find and return all client's accounts
     *
     * @param parentId
     * @return List<Account>
     * @throws SQLException
     */
    List<Account> getAll(int parentId) throws SQLException;

    boolean delete(int clientId, int id) throws SQLException;
}
