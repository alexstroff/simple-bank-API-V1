package com.bank.service;

import com.bank.model.Account;
import com.bank.model.CreditCard;
import com.bank.repository.CreditCardRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;

import java.sql.SQLException;
import java.util.List;

public class CreditCardService implements ServiceInterface {

    private CreditCardRepositoryImpl cardRepository;
    private TxManager txManager;

    public CreditCardService() {
        this.cardRepository = new CreditCardRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public List<CreditCard> getAll(Object o) {
        int accountId= (int) o;
        List<CreditCard> cards = null;
        try {
            cards = cardRepository.getAllCards(accountId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    @Override
    public CreditCard getById(Object o) {
        int id = (int) o;
        CreditCard card = new CreditCard();
        try {
            card = cardRepository.getCardById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

    @Override
    public void update(Object o) {
        CreditCard card = (CreditCard) o;
        try {
            cardRepository.updateCard(card);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public boolean delete(Object o) {
        int cardId = (int) o;
        try {
            if (txManager.doInTransaction(() -> cardRepository.deleteCard(cardId))) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void add(Object o, Object b) {
        int accountId = (int) o;
        CreditCard card = (CreditCard) b;
        try {
            cardRepository.addCard(accountId, card);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void add(Object o) {

    }
}
