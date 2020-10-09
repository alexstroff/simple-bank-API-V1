package com.bank.rest;

import com.bank.model.CreditCard;
import com.bank.repository.utils.DBUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.utils.HttpServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.h2.tools.RunScript;
import org.junit.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.*;
import static com.bank.CreditCardTestData.*;
import static org.junit.Assert.assertEquals;

@Slf4j
public class CreditCardRestControllerTest {
    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() {
        server = HttpServerUtils.startServer();
        javax.ws.rs.client.Client c = ClientBuilder.newClient();
        target = c.target(HttpServerUtils.BASE_URI);
    }

    @Before
    public void setup() {
        try (Connection connection = DBUtils.getDataSource().getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }

    @Test
    public void getById() {
        String URL = String.format("/client/%s/account/%s/card/%s/", CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID);
        log.debug(URL);
        String s = target.path(URL).request().get(String.class);
        log.debug("Got={}", s);
        CreditCard card = JacksonUtils.readValue(s, CreditCard.class);
        CARD_MATCHER.assertMatch(CARD_1, card);
    }

    @Test
    public void getAll() {
        String CARD_GET_ALL_URL = String.format("/client/%s/account/%s/card/all", CLIENT_1_ID, ACCOUNT_1_ID);
        String s = target.path(CARD_GET_ALL_URL).request().get(String.class);
        List<CreditCard> accountList = JacksonUtils.readValues(s, CreditCard.class);
        Assert.assertEquals(1, accountList.size());
        CreditCard card = accountList.get(0);
        CARD_MATCHER.assertMatch(card, CARD_1);
    }

    @Test
    public void addAccount() {
        String URL = String.format("/client/%s/account/%s/card", CLIENT_1_ID, ACCOUNT_1_ID);

        String CARD_GET_ALL_URL = String.format("/client/%s/account/%s/card/all", CLIENT_1_ID, ACCOUNT_1_ID);
        String oldGetAll = target.path(CARD_GET_ALL_URL).request().get(String.class);
        List<CreditCard> oldCardList = JacksonUtils.readValues(oldGetAll, CreditCard.class);

        Assert.assertEquals(1, oldCardList.size());

        CreditCard newCard = CreditCard.builder().number("123").account(ACCOUNT_1).build();

        Response response = target.path(URL).request()
                .post(Entity.entity(newCard, MediaType.APPLICATION_JSON));

        assertEquals("should return status 201", 201, response.getStatus());

        String newGetAll = target.path(CARD_GET_ALL_URL).request().get(String.class);
        List<CreditCard> newCardList = JacksonUtils.readValues(newGetAll, CreditCard.class);
        Assert.assertEquals(2, newCardList.size());

        newCardList.removeAll(oldCardList);

        CreditCard savedCard = newCardList.get(0);
        newCard.setId(savedCard.getId());
        CARD_MATCHER.assertMatch(newCard, savedCard);
    }

    @Test
    public void update() {
        String URL = String.format("/client/%s/account/%s/card", CLIENT_1_ID, ACCOUNT_1_ID);
        CreditCard card = new CreditCard(CARD_1);
        card.setNumber("8888888888888");
        Response response = target.path(URL).request()
                .put(Entity.entity(card, MediaType.APPLICATION_JSON));
        assertEquals("should return status 202", 202, response.getStatus());

        String URL_get = String.format("/client/%s/account/%s/card/%s/", CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID);
        String s = target.path(URL_get).request().get(String.class);
        CreditCard updatedCard = JacksonUtils.readValue(s, CreditCard.class);

        CARD_MATCHER.assertMatch(card, updatedCard);
    }

    @Test
    public void deleteAccount() {
        String URL = String.format("/client/%s/account/%s/card/%s/", CLIENT_1_ID, ACCOUNT_1_ID, CARD_1_ID);

        CreditCard newCard = CreditCard.builder().number("123").account(ACCOUNT_1).build();

        String addURL = String.format("/client/%s/account/%s/card", CLIENT_1_ID, ACCOUNT_1_ID);
        Response response = target.path(addURL).request()
                .post(Entity.entity(newCard, MediaType.APPLICATION_JSON));

        String CARD_GET_ALL_URL = String.format("/client/%s/account/%s/card/all", CLIENT_1_ID, ACCOUNT_1_ID);
        String oldGetAll = target.path(CARD_GET_ALL_URL).request().get(String.class);
        List<CreditCard> oldCardList = JacksonUtils.readValues(oldGetAll, CreditCard.class);

        Response delete = target.path(URL).request().delete();
        assertEquals("should return status 200", 200, delete.getStatus());

        String newGetAll = target.path(CARD_GET_ALL_URL).request().get(String.class);
        List<CreditCard> newCardList = JacksonUtils.readValues(newGetAll, CreditCard.class);
        Assert.assertEquals(1, newCardList.size());
    }
}