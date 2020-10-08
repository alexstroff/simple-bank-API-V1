package com.bank.service;

import com.bank.model.Account;
import com.bank.model.CreditCard;
import com.bank.repository.CreditCardRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CreditCardServiceImpl implements CreditCardsService {

    private CreditCardRepositoryImpl cardRepository;
    private TxManager txManager;

    public CreditCardServiceImpl() {
        this.cardRepository = new CreditCardRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public CreditCard getById(int id) {
        log.trace("id={}", id);

        CreditCard card = new CreditCard();
        try {
            card = cardRepository.getById(id);
        } catch (SQLException e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", card);
        return card;
    }

    @Override
    public List<CreditCard> getAll(int accountId) {
        log.trace("id={}", accountId);

        List<CreditCard> cards = null;
        try {
            cards = cardRepository.getAll(accountId);
        } catch (SQLException e) {
            log.warn("id={}", accountId, e);
        }
        log.trace("returning={}", cards);

        return cards;
    }

    @Override
    public CreditCard save(CreditCard card) {
        CreditCard card1 = new CreditCard();
        try {
            card1 = cardRepository.save(card);
        } catch (SQLException e) {
            log.warn("Card was not created!", e);
        }
        log.trace("returning={}", card1);

        return card1;
    }

    @Override
    public boolean delete(int cardId) {
        log.trace("got={}", cardId);

        boolean success = false;
        try {
            if (txManager.doInTransaction(() -> cardRepository.delete(cardId))) success = true;
        } catch (Exception e) {
            log.warn("Card was not deleted!", e);
        }
        log.trace("returning={}", success);
        return success;
    }
}
