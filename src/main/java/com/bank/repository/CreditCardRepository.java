package com.bank.repository;

import com.bank.model.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardRepository {

    /**
     * Get CreditCard by Id.
     *
     * @param clientId
     * @param accountId
     * @param creditCardId
     * @return CreditCard.
     * @throws SQLException if CreditCard with Id not found.
     */
    CreditCard getById(int clientId, int accountId, int creditCardId) throws SQLException;

    /**
     * Returns all card for this accountId.
     *
     * @param clientId
     * @param accountId
     * @return List<CreditCard>
     * @throws SQLException if DB exceptions
     */
    List<CreditCard> getAll(int clientId, int accountId) throws SQLException;

    /**
     * Persist CreditCard.
     *
     * @param creditCard
     * @return creditCard
     * @throws SQLException if CreditCard Id not found or database did not allocate a number.
     */
    CreditCard save(CreditCard creditCard) throws SQLException;

    /**
     * Delete Card by Id.
     * @param accountId
     * @param cardId
     * @return true if success.
     * @throws SQLException if Card Id not found.
     */
    boolean delete(int accountId, int cardId) throws SQLException;

    /**
     * Det ClientId by AccountId
     * @param accountId
     * @return Client id.
     * @throws SQLException if DB exceptions
     */
    int getClientIdByAccountId(int accountId) throws SQLException;
}
