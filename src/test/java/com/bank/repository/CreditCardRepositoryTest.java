package com.bank.repository;

import com.bank.ClientTestData;
import com.bank.model.CreditCard;
import com.bank.repository.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.*;
import static com.bank.CreditCardTestData.*;

@Slf4j
public class CreditCardRepositoryTest {

    private static CreditCardRepository repository;

    @BeforeClass
    public static void setup() {
        repository = new CreditCardRepositoryImpl();
    }

    @Before
    public void setUp() {
        try (Connection connection = DBUtils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getById() throws SQLException {
        CreditCard card = repository.getById(CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID);
        log.debug("get clientId={}, accountId={}, creditCardId={}, returns={}",
                CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID, card);
        CARD_MATCHER.assertMatch(card, CARD_1);
    }

    @Test
    public void getAll() throws SQLException {
        List<CreditCard> creditCards = repository.getAll(CLIENT_1_ID, ACCOUNT_1_ID);
        log.debug("returns={}", creditCards);
        CARD_MATCHER.assertMatch(creditCards, CARD_1);
    }

    @Test
    public void add() throws SQLException {
        CreditCard newCard = CreditCard.builder().number("123").account(ACCOUNT_1).build();
        log.debug("Inserting={}", newCard);
        CreditCard savedCard = repository.save(newCard);
        log.debug("savedCard={}", savedCard);
        newCard.setId(savedCard.getId());
        CARD_MATCHER.assertMatch(savedCard, newCard);
    }

    @Test
    public void updateCard() throws SQLException {
        CreditCard card = new CreditCard(CARD_1);
        card.setNumber("321543");
        CreditCard saved = repository.save(card);
        log.debug("get={}, returns={}", card, saved);
        CARD_MATCHER.assertMatch(saved, card);
    }

    @Test
    public void deleteCard() throws SQLException {
        CreditCard savedCard = repository.save(CreditCard.builder().account(ACCOUNT_1).number("0000010111").build());
        List<CreditCard> oldCardList = repository.getAll(CLIENT_1_ID, ACCOUNT_1_ID);
        log.debug("before delete={}", oldCardList);
        repository.delete(savedCard.getAccount().getId(), savedCard.getId()); //100006
        List<CreditCard> newCardList = repository.getAll(CLIENT_1_ID, ACCOUNT_1_ID);
        log.debug("after delete={}", oldCardList);
        Assert.assertEquals(1, newCardList.size());
        CARD_MATCHER.assertMatch(CARD_1, newCardList.get(0));
    }

    @Test
    public void getClientIdByAccountId() throws SQLException {
        Assert.assertEquals(CLIENT_1_ID, repository.getClientIdByAccountId(ACCOUNT_1_ID));
    }
}