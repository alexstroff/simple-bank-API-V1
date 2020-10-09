package com.bank.service;

import com.bank.model.CreditCard;

import java.util.List;

public interface CreditCardService {

    /**
     * Get CreditCard by Id.
     *
     * @param clientId
     * @param accountId
     * @param cardId
     * @return CreditCard.
     */
    CreditCard getById(int clientId, int accountId, int cardId);

    /**
     * Returns all card for this accountId.
     *
     * @param clientId
     * @param accountId
     * @return List<CreditCard>
     */
    List<CreditCard> getAll(int clientId, int accountId);

    /**
     * Persist CreditCard.
     *
     * @param creditCard
     * @return creditCard
     */
    CreditCard save(CreditCard creditCard);

    /**
     * Delete Card by Id.
     *
     * @param clientId
     * @param accountId
     * @param cardId
     * @return true if success.
     */
    boolean delete(int clientId, int accountId, int cardId);

}
