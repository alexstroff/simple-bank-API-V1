package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardRepository {

    /**
     * @param card    add new card
     */
    void addCard(int accountId, CreditCard card) throws SQLException;

    /**
     * @param accountId
     * @return find all account's card
     */
    List<CreditCard> getAllCards(int accountId) throws SQLException;

    /**
     * @param cardId
     * @return find cadr by id
     */
    CreditCard getCardById(int cardId) throws SQLException;

    /**
     * @param card update card
     */
    void updateCard(CreditCard card) throws SQLException;

    /**
     * @return delete card
     */
    boolean deleteCard(int cardId) throws SQLException;


}
