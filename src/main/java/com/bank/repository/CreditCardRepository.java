package com.bank.repository;

import com.bank.model.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardRepository extends BaseRepository<CreditCard> {

    /**
     * Returns all card for this accountId
     *
     * @param accountId
     * @return List<CreditCard>
     * @throws SQLException
     */
    List<CreditCard> getAll(int accountId) throws SQLException;
}
