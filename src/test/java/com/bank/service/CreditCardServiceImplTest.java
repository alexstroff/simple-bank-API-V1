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
import static com.bank.CreditCardTestData.*;

@Slf4j
public class CreditCardServiceImplTest {

    private static CreditCardServiceImpl service;

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
        CreditCard card = service.getById(CARD_1_ID);
        log.debug("id={}, returns=", card);
        CARD_MATCHER.assertMatch(card, CARD_1);
    }

    @Test
    public void getAllCards() {
        List<CreditCard> creditCards = service.getAll(ACCOUNT_1.getId());
        log.debug("returns={}", creditCards);
        CARD_MATCHER.assertMatch(creditCards, CARD_1);
    }

    @Test
    public void addCard() {
        CreditCard newCard = CreditCard.builder().number("123").account(ACCOUNT_1).build();
        log.debug("Inserting={}", newCard);
        CreditCard savedCard = service.save(newCard);
        log.debug("savedCard={}", savedCard);
        newCard.setId(savedCard.getId());
        CARD_MATCHER.assertMatch(savedCard, newCard);
    }

    @Test
    public void updateCard() {
        CreditCard card = new CreditCard(CARD_1);
        card.setNumber("321543");
        CreditCard saved = service.save(card);
        log.debug("get={}, returns={}", card, saved);
        CARD_MATCHER.assertMatch(saved, card);
    }

    @Test
    public void deleteCard() {
        CreditCard savedCard = service.save(CreditCard.builder().account(ACCOUNT_1).number("0000010111").build());
        List<CreditCard> oldCardList = service.getAll(ACCOUNT_1_ID);
        log.debug("before delete={}", oldCardList);
        service.delete(savedCard.getId()); //100006
        List<CreditCard> newCardList = service.getAll(ACCOUNT_1_ID);
        Assert.assertEquals(1, newCardList.size());
        CARD_MATCHER.assertMatch(CARD_1, newCardList.get(0));
    }

}