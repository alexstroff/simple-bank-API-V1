package com.bank.service;

import com.bank.model.CreditCard;
import com.bank.repository.CreditCardRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CreditCardServiceImpl implements CreditCardsInterface {

    private CreditCardRepositoryImpl cardRepository;
    private TxManager txManager;

    public CreditCardServiceImpl() {
        this.cardRepository = new CreditCardRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }


    @Override
    public List<CreditCard> getAll(int accountId) {
        log.trace("id={}", accountId);

        List<CreditCard> cards = null;
        try {
            cards = cardRepository.getAllCards(accountId);
        } catch (SQLException e) {
            log.warn("id={}", accountId, e);
        }
        log.trace("returning={}", cards);

        return cards;
    }

    @Override
    public CreditCard getById(int id) {
        log.trace("id={}", id);

        CreditCard card = new CreditCard();
        try {
            card = cardRepository.getCardById(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", card);
        return card;
    }

    @Override
    public CreditCard update(CreditCard card) {
        log.trace("got={}", card);

        try {
            cardRepository.updateCard(card);
        } catch (SQLException e) {
            log.warn("Card was not updated!", e);
        }
        log.trace("returning={}", card);

        return card;
    }

    @Override
    public boolean delete(int cardId) {
        log.trace("got={}", cardId);

        boolean success = false;
        try {
            if (txManager.doInTransaction(() -> cardRepository.deleteCard(cardId))) success = true;
        } catch (Exception e) {
            log.warn("Card was not deleted!", e);
        }
        log.trace("returning={}", success);
        return success;
    }

    @Override
    public CreditCard add(int accountId, CreditCard card) {
        log.trace("got={}", card);

        CreditCard card1 = new CreditCard();
        try {
            card1 = cardRepository.addCard(accountId, card);
        } catch (SQLException e) {
            log.warn("Card was not created!", e);
        }
        log.trace("returning={}", card1);

        return card1;
    }

    @Override
    public CreditCard add(CreditCard card) {
        return null;
    }
}
