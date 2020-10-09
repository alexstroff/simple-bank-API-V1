package com.bank.rest;

import com.bank.AccountTestData;
import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.to.AccountTO;
import com.bank.model.to.ClientTo;
import com.bank.model.utils.EntityUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.utils.HttpServerUtils;
import com.bank.repository.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.h2.tools.RunScript;
import org.junit.*;

import javax.ws.rs.*;
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
    public static void setUp() throws Exception {
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
    public static void tearDown() throws Exception {
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
        String URL = String.format("/client/%s/account/all", CLIENT_1_ID);
        String s = target.path(URL).request().get(String.class);
        List<Account> accountList = JacksonUtils.readValues(s, Account.class);
        Assert.assertEquals(1, accountList.size());
        Account account = accountList.get(0);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
    }

    @Test
    public void addAccount() {
        Account account = Account.builder().id(100006).number("123456").amount(new BigDecimal(10999).setScale(1)).currency("RUB").build();
        Response response = target.path("/client/100000/account/add").request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
        String URL = String.format("/client/%s/account/%s/", 100000, 100006);
        String s1 = target.path(URL).request().get(String.class);
        Account account1 = JacksonUtils.readValue(s1, Account.class);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, account1);
    }

    @Test
    public void update() {
        String URL = String.format("/client/%s/account/%s/", 100000, 100002);
        String s = target.path(URL).request().get(String.class);
        Account account = JacksonUtils.readValue(s, Account.class);
        account.setAmount(new BigDecimal(9));

        target.path("/client/100000/account/update").request()
                .put(Entity.entity(account, MediaType.APPLICATION_JSON));
        String URL1 = String.format("/client/%s/account/%s/", 100000, 100002);
        String s1 = target.path(URL1).request().get(String.class);
        Account account1 = JacksonUtils.readValue(s1, Account.class);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, account1);

    }

    @Test
    public void delete() {
        Account account = Account.builder().id(100006).number("123456").amount(new BigDecimal(10999).setScale(1)).currency("RUB").build();
        target.path("/client/100000/account/add").request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
        String URL = String.format("/client/%s/account/all", 100000);
        String s = target.path(URL).request().get(String.class);
        List<Account> accountList = JacksonUtils.readValues(s, Account.class);

        String URL1 = String.format("/client/%s/account/delete/%s", 100000, 100006);
        target.path(URL1).request().delete(String.class);

        String s2 = target.path(URL).request().get(String.class);

        List<Account> accountList1 = JacksonUtils.readValues(s2, Account.class);

        Assert.assertEquals(1, accountList1.size());


    }


//    @POST
//    @Path("/add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String addAccount(@PathParam("clientId") int clientId, Account account) {
//        Account account1 = accountService.add(clientId, account);
//        return JacksonUtils.writeValue(account1.getId());
//    }


}