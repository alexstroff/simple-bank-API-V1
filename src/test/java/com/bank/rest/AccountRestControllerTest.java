package com.bank.rest;

import com.bank.HttpServerUtils;
import com.bank.repository.utils.DBUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.h2.tools.RunScript;
import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AccountRestControllerTest {

    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() throws Exception {
        server = HttpServerUtils.startServer();
        Client c = ClientBuilder.newClient();
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
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getIt() {
        String responseMsg = target.path("account").request().get(String.class);
            assertEquals("Account got it!", responseMsg);
    }

}