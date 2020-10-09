package com.bank.repository;

import com.bank.model.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardRepository extends BaseRepository<CreditCard> {

    CreditCard getById(int clientId, int accountId, int creditCardId) throws SQLException;

    /**
     * Returns all card for this accountId
     *
     * @param accountId
     * @return List<CreditCard>
     * @throws SQLException
     */
    List<CreditCard> getAll(int clientId, int accountId) throws SQLException;

    boolean delete(int accountId, int cardId) throws SQLException;

    int getClientIdByAccountId(int accountId) throws SQLException;
}
