package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CreditCardRepository {

    /**
     * @param account
     * @param card    add new card
     */
    void addCard(Account account, CreditCard card) throws SQLException;

    /**
     * @param account
     * @return find all account's card
     */
    List<CreditCard> getAllCards(Account account) throws SQLException;

    /**
     * @param card
     * @return find cadr by id
     */
    CreditCard getCardById(CreditCard card) throws SQLException;

    /**
     * @param card update card
     */
    void updateCard(CreditCard card) throws SQLException;

    /**
     * @param card
     * @return delete card
     */
    boolean deleteCard(CreditCard card) throws SQLException;


}
