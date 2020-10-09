package com.bank.service;

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

import static com.bank.AccountTestData.ACCOUNT_1;
import static com.bank.AccountTestData.ACCOUNT_1_ID;
import static com.bank.ClientTestData.CLIENT_1_ID;
import static com.bank.CreditCardTestData.*;

@Slf4j
public class CreditCardServiceImplTest {

    private static CreditCardService service;

    @BeforeClass
    public static void setup() {
        service = new CreditCardServiceImpl();
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
    public void getById() {
        CreditCard card = service.getById(CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID);
        log.debug("get clientId={}, accountId={}, creditCardId={}, returns={}",
                CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID, card);
        CARD_MATCHER.assertMatch(card, CARD_1);
    }

    @Test
    public void getAll() {
        List<CreditCard> creditCards = service.getAll(CLIENT_1_ID, ACCOUNT_1_ID);
        log.debug("returns={}", creditCards);
        CARD_MATCHER.assertMatch(creditCards, CARD_1);
    }

    @Test
    public void add() {
        CreditCard newCard = CreditCard.builder().number("123").account(ACCOUNT_1).build();
        log.debug("Inserting={}", newCard);
        CreditCard savedCard = service.save(newCard);
        log.debug("savedCard={}", savedCard);
        newCard.setId(savedCard.getId());
        CARD_MATCHER.assertMatch(savedCard, newCard);
    }

    @Test
    public void update() {
        CreditCard card = new CreditCard(CARD_1);
        card.setNumber("321543");
        CreditCard saved = service.save(card);
        log.debug("get={}, returns={}", card, saved);
        CARD_MATCHER.assertMatch(saved, card);
    }

    @Test
    public void delete() {
        CreditCard savedCard = service.save(CreditCard.builder().account(ACCOUNT_1).number("0000010111").build());
        List<CreditCard> oldCardList = service.getAll(CLIENT_1_ID, ACCOUNT_1_ID);
        log.debug("before delete={}", oldCardList);
        service.delete(savedCard.getAccount().getClient().getId(), savedCard.getAccount().getId(), savedCard.getId()); //100006
        List<CreditCard> newCardList = service.getAll(CLIENT_1_ID, ACCOUNT_1_ID);
        log.debug("after delete={}", oldCardList);
        Assert.assertEquals(1, newCardList.size());
        CARD_MATCHER.assertMatch(CARD_1, newCardList.get(0));
    }
}