package com.bank.repository;

import com.bank.AccountTestData;
import com.bank.CreditCardTestData;
import com.bank.model.Account;
import com.bank.model.CreditCard;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CreditCardRepositoryTest {

    private static CreditCardRepository creditCardRepository;

    @BeforeClass
    public static void setup() {
        creditCardRepository = new CreditCardRepositoryImpl();
    }

    @Before
    public void setUp() {
        try (Connection connection = Utils.getConnection()) {
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
            creditCardRepository.addCard(account, card);
            List<CreditCard> allCards = creditCardRepository.getAllCards(account);
            CreditCardTestData.CARD_MATCHER.assertMatch(allCards, CreditCardTestData.CARD_1, card);
    }

    @Test
    public void getAllCards() throws SQLException {
//            creditCardRepository.addCard(AccountTestData.ACCOUNT_1, CreditCardTestData.CARD_1);
            List<CreditCard> creditCards = creditCardRepository.getAllCards(AccountTestData.ACCOUNT_1);
            CreditCardTestData.CARD_MATCHER.assertMatch(creditCards, CreditCardTestData.CARD_1);
    }

    @Test
    public void getCardById() {
    }

    @Test
    public void updateCard() {
    }

    @Test
    public void deleteCard() {
    }
}