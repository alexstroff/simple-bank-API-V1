package com.bank.service;

import com.bank.model.Client;
import com.bank.model.CreditCard;
import com.bank.repository.CreditCardRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;

import java.sql.SQLException;
import java.util.List;

public class CreditCardServiceImpl implements CreditCardsInterface {

    private CreditCardRepositoryImpl cardRepository;
    private TxManager txManager;

    public CreditCardServiceImpl() {
        this.cardRepository = new CreditCardRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }


    @Override
    public List<CreditCard> getAll(int accountId) {
        List<CreditCard> cards = null;
        try {
            cards = cardRepository.getAllCards(accountId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    @Override
    public CreditCard getById(int id) {
        CreditCard card = new CreditCard();
        try {
            card = cardRepository.getCardById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

    @Override
    public CreditCard update(CreditCard card) {
        try {
            cardRepository.updateCard(card);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card;
    }

    @Override
    public boolean delete(int cardId) {
        try {
            if (txManager.doInTransaction(() -> cardRepository.deleteCard(cardId))) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CreditCard add(int accountId, CreditCard card) {
        CreditCard card1 = new CreditCard();
        try {
             card1 = cardRepository.addCard(accountId, card);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return card1;
    }

    @Override
    public CreditCard add(CreditCard card) {
        return null;
    }
}
