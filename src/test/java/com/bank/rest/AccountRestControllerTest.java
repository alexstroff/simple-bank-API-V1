package com.bank.rest;

import com.bank.model.Account;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.utils.HttpServerUtils;
import com.bank.repository.utils.DBUtils;
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.*;
import static org.junit.Assert.*;

@Slf4j
public class AccountRestControllerTest {

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
        try (Connection connection = DBUtils.getDataSource().getConnection()){
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

    //"id":100002,"number":"1111111111","amount":1000.0,"currency":"RUB"
    @Test
    public void getById() {
        String URL = String.format("/client/%s/account/%s/", CLIENT_1_ID, ACCOUNT_1_ID);
        String s = target.path(URL).request().get(String.class);
        log.debug("Got={}", s);
        Account account = JacksonUtils.readValue(s, Account.class);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(ACCOUNT_1, account);
    }

    @Test
    public void getAll() {
        String ACCOUNT_GET_ALL_URL = String.format("/client/%s/account/all", CLIENT_1_ID);
        String s = target.path(ACCOUNT_GET_ALL_URL).request().get(String.class);
        List<Account> accountList = JacksonUtils.readValues(s, Account.class);
        Assert.assertEquals(1, accountList.size());
        Account account = accountList.get(0);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
    }

    @Test
    public void add() {
        String URL = String.format("/client/%s/account/", CLIENT_1_ID);

        String oldGetAll = target.path(String.format("/client/%s/account/all", CLIENT_1_ID)).request().get(String.class);
        List<Account> oldAccountList = JacksonUtils.readValues(oldGetAll, Account.class);
        Assert.assertEquals(1, oldAccountList.size());

        Account account = Account.builder()
                .client(CLIENT_1)
                .number("40817810500550987654")
                .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                .currency("RUB")
                .build();

        Response response = target.path(URL).request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        assertEquals("should return status 201", 201, response.getStatus());

        String newGetAll = target.path(String.format("/client/%s/account/all", CLIENT_1_ID)).request().get(String.class);
        List<Account> newAccountList = JacksonUtils.readValues(newGetAll, Account.class);
        Assert.assertEquals(2, newAccountList.size());

        newAccountList.removeAll(oldAccountList);
        Account newAccount = newAccountList.get(0);
        account.setId(newAccount.getId());
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(newAccount, account);
    }

    @Test
    public void update() {
        String URL = String.format("/client/%s/account/", CLIENT_1_ID);

        Account account = new Account(ACCOUNT_1);
        account.setAmount( new BigDecimal(999).setScale(2));
        Response response = target.path(URL).request()
                .put(Entity.entity(account, MediaType.APPLICATION_JSON));

        assertEquals("should return status 202", 202, response.getStatus());

        String URL_cl = String.format("/client/%s/account/%s/", CLIENT_1_ID, ACCOUNT_1_ID);
        String s1 = target.path(URL_cl).request().get(String.class);
        Account updatedAccount = JacksonUtils.readValue(s1, Account.class);

        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(updatedAccount, account);
    }

    @Test
    public void delete() {
        String URL = String.format("/client/%s/account/%s", CLIENT_1_ID, ACCOUNT_1_ID);

        Account account = Account.builder()
                .client(CLIENT_1)
                .number("40817810500550987654")
                .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                .currency("RUB")
                .build();

        target.path(String.format("/client/%s/account/", CLIENT_1_ID)).request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        String oldGetAll = target.path(String.format("/client/%s/account/all", CLIENT_1_ID)).request().get(String.class);
        List<Account> oldAccountList = JacksonUtils.readValues(oldGetAll, Account.class);
        Assert.assertEquals(2, oldAccountList.size());

        Response delete = target.path(URL).request().delete();
        assertEquals("should return status 200", 200, delete.getStatus());

        String newGetAll = target.path(String.format("/client/%s/account/all", CLIENT_1_ID)).request().get(String.class);
        List<Account> newAccountList = JacksonUtils.readValues(newGetAll, Account.class);
        Assert.assertEquals(1, newAccountList.size());
    }
}