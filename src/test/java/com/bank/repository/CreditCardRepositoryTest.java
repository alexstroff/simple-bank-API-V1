package com.bank.repository;

import com.bank.AccountTestData;
import com.bank.CreditCardTestData;
import com.bank.model.Account;
import com.bank.model.CreditCard;
import com.bank.repository.utils.DBUtils;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditCardRepositoryTest {

    private static CreditCardRepository creditCardRepository;

    @BeforeClass
    public static void setup() {
        creditCardRepository = new CreditCardRepositoryImpl();
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
    public void addCard() throws SQLException {
            Account account = AccountTestData.ACCOUNT_1;
            CreditCard card = CreditCard.builder().id(100006).number("123").registered(null).account(account).build();
            creditCardRepository.addCard(account.getId(), card);
            List<CreditCard> allCards = creditCardRepository.getAllCards(account.getId());
            CreditCardTestData.CARD_MATCHER.assertMatch(allCards, CreditCardTestData.CARD_1, card);
    }

    @Test
    public void getAllCards() throws SQLException {
            List<CreditCard> creditCards = creditCardRepository.getAllCards(AccountTestData.ACCOUNT_1.getId());
            CreditCardTestData.CARD_MATCHER.assertMatch(creditCards, CreditCardTestData.CARD_1);
    }

    @Test
    public void getCardById() throws SQLException {
        CreditCard card = CreditCard.builder().id(100004).number("9991111111").build();
        CreditCard card1 = creditCardRepository.getCardById(100004);
        CreditCardTestData.CARD_MATCHER.assertMatch(card, card1);
    }

    @Test
    public void updateCard() throws SQLException {
        CreditCard card = CreditCard.builder().id(100004).number("9991111111").build();
        card.setNumber("321543");
        creditCardRepository.updateCard(card);
        CreditCard card1 = creditCardRepository.getCardById(100004);
        CreditCardTestData.CARD_MATCHER.assertMatch(card, card1);
    }

    @Test
    public void deleteCard() throws SQLException {
        List<CreditCard> cards = creditCardRepository.getAllCards(100002);
        creditCardRepository.deleteCard(100004);
        List<CreditCard> cards1 = creditCardRepository.getAllCards(100002);
        Assert.assertNotEquals(cards, cards1);
    }
}