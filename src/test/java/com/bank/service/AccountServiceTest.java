package com.bank.service;

import com.bank.model.CreditCard;
import com.bank.repository.Utils;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.*;
import static com.bank.CreditCardTestData.*;

public class AccountServiceTest {

    private static AccountService service;

    @BeforeClass
    public static void setup() {
        service = new AccountService(Utils.getDataSource());
    }

    @Before
    public void setUp() throws Exception {
        try (Connection connection = Utils.getConnection()){
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void checkBalance() {
//        BigDecimal balance = service.(ACCOUNT_1);
//        Assert.assertEquals(0, ACCOUNT_1.getAmount().compareTo(balance));
//    }
//
//    @Test
//    public void depositeFunds() {
//        BigDecimal deposite = new BigDecimal(3000);
//        BigDecimal balance = service.checkBalance(ACCOUNT_1).add(deposite);
//        service.depositeFunds(ACCOUNT_1, deposite);
//        BigDecimal newBalance = service.checkBalance(ACCOUNT_1);
//        Assert.assertEquals(0, newBalance.compareTo(balance));
//
//    }
//
//    @Test
//    public void getListOfCreditCards() {
//        List<CreditCard> clientOneCards = service.getListOfCreditCards(CLIENT_1);
//        Assert.assertEquals(1, clientOneCards.size());
//        CARD_MATCHER.assertMatch(clientOneCards.get(0), CARD_1);
//    }
//
//    @Test
//    public void creditCardIssue() {
//        List<CreditCard> cardList = service.getListOfCreditCards(CLIENT_1);
//        CreditCard issuedCard = service.creditCardIssue(ACCOUNT_1);
//        List<CreditCard> newCardList = service.getListOfCreditCards(CLIENT_1);
//        Assert.assertEquals(cardList.size() + 1, newCardList.size());
//
//        newCardList.removeAll(cardList);
//        CARD_MATCHER.assertMatch(newCardList.get(0), issuedCard);
//    }
}