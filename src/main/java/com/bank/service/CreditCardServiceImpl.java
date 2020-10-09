package com.bank.service;

import com.bank.model.CreditCard;
import com.bank.repository.CreditCardRepository;
import com.bank.repository.CreditCardRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepository repository;
    private TxManager txManager;

    public CreditCardServiceImpl() {
        this.repository = new CreditCardRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public CreditCard getById(int parentId, int entityId, int child) {
        log.trace("clientId={}, accountId={}, cardId={}", parentId, entityId, child);
        CreditCard creditCard = null;
        try {
            creditCard = txManager.doInTransaction(() -> repository.getById(parentId, entityId, child));
        } catch (Exception e) {
            log.warn("parentId={}, entityId={}, cardId={}", parentId, entityId, child, e);
        }
        log.trace("returning={}", creditCard);
        return creditCard;
    }


//    @Override
//    public CreditCard getById(int id) {
//        log.trace("id={}", id);
//
//        CreditCard card = new CreditCard();
//        try {
//            card = cardRepository.getById(id);
//        } catch (SQLException e) {
//            log.warn("id={}", id, e);
//        }
//        log.trace("returning={}", card);
//        return card;
//    }

    @Override
    public List<CreditCard> getAll(int clientId, int accountId) {
        log.trace("clientId={}, accountId={}", clientId, accountId);

        List<CreditCard> cards = null;
        try {
            cards = repository.getAll(clientId, accountId);
        } catch (SQLException e) {
            log.warn("id={}", accountId, e);
        }
        log.trace("returning={}", cards);

        return cards;
    }

//    @Override
//    public List<CreditCard> getAll(int accountId) {
//        log.trace("id={}", accountId);
//
//        List<CreditCard> cards = null;
//        try {
//            cards = repository.getAll(accountId);
//        } catch (SQLException e) {
//            log.warn("id={}", accountId, e);
//        }
//        log.trace("returning={}", cards);
//
//        return cards;
//    }

    @Override
    public CreditCard save(CreditCard card) {
        CreditCard card1 = new CreditCard();
        try {
            card1 = repository.save(card);
            txManager.doInTransaction(() -> {
                int clientIdByAccountId = repository.getClientIdByAccountId(card.getAccount().getId());
                int clientIdByUser = card.getAccount().getClient().getId();
                if (clientIdByAccountId != clientIdByUser) {
                    throw new IllegalArgumentException("You can't operate with credit card of another user");
                }
                return repository.save(card);
            });
        } catch (Exception e) {
            log.warn("Card was not created!", e);
        }
        log.trace("returning={}", card1);

        return card1;
    }


    @Override
    public boolean delete(int clientId, int accountId, int cardId) {
        log.trace("clientId={}, accountId={}, cardId={}", cardId, accountId, cardId);

        boolean success = false;
        try {
            if (txManager.doInTransaction(() -> {
                int clientIdByAccountId = repository.getClientIdByAccountId(accountId);
                if (clientIdByAccountId != clientId) {
                    throw new IllegalArgumentException("You can't delete credit card of another user");
                }
                return repository.delete(accountId, cardId);
            })) {
                success = true;
            }
        } catch (Exception e) {
            log.warn("Card was not deleted!", e);
        }
        log.trace("returning={}", success);
        return success;
    }

    @Override
    public boolean delete(int cardId) {
        log.trace("got={}", cardId);

        boolean success = false;
        try {
            if (txManager.doInTransaction(() -> repository.delete(cardId))) success = true;
        } catch (Exception e) {
            log.warn("Card was not deleted!", e);
        }
        log.trace("returning={}", success);
        return success;
    }
}
