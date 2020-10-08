package com.bank.service;

import com.bank.AccountTestData;
import com.bank.CreditCardTestData;
import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;
import com.bank.repository.utils.DBUtils;
import junit.framework.TestCase;
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
import java.util.ArrayList;
import java.util.List;

public class CreditCardServiceTest {

    private static CreditCardService service;

    @BeforeClass
    public static void setup() {
        service = new CreditCardService();
    }

    @Before
    public void setUp() throws Exception {
        try (Connection connection = DBUtils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAll() {
        CreditCard card = CreditCard.builder().id(100004).number("9991111111").build();
        List<CreditCard> cards = new ArrayList<>();
        cards.add(card);
        List<CreditCard> cards1 = service.getAll(100002);
        CreditCardTestData.CARD_MATCHER.assertMatch(cards, cards1);
    }
}