package com.bank.rest;

import com.bank.model.Client;
import com.bank.model.to.ClientTo;
import com.bank.repository.Utils;
import com.bank.rest.JacksonUtils.JacksonUtils;
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

import static com.bank.ClientTestData.*;
import static org.junit.Assert.*;

public class ClientRestControllerTest {

    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() throws Exception {
        server = Utils.startServer();
        javax.ws.rs.client.Client c = ClientBuilder.newClient();
        target = c.target(Utils.BASE_URI);
    }

    @Before
    public void setup() {
        try (Connection connection = Utils.getDataSource().getConnection()) {
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

    //{"id":100000,"name":"Vasay","email":"vasyaTheGreat@mail.ru","registered":"05.10.2020"}
    @Test
    public void getById() {
        String s = target.path("/client/" + CLIENT_1_ID).request().get(String.class);
        Client client = JacksonUtils.readValue(s, Client.class);
        CLIENTS_MATCHER.assertMatch(CLIENT_1, client);
    }

    //[{"id":100000,"name":"Vasay","email":"vasyaTheGreat@mail.ru","registered":"06.10.2020"},{"id":100001,"name":"Petya","email":"petayTheBest@yandex.ru","registered":"06.10.2020"}]
    @Test
    public void getAll() {
        String s = target.path("/client/all").request().get(String.class);
        List<Client> clientList = JacksonUtils.readValues(s, Client.class);
        CLIENTS_MATCHER.assertMatch(clientList, CLIENTS);
    }

    //100006
    @Test
    public void add() {
        String s = target.path("/client/all").request().get(String.class);
        List<Client> oldClientList = JacksonUtils.readValues(s, Client.class);

        ClientTo client = new ClientTo(CLIENT_3);

        Response response = target.path("/client/add").request()
                .post(Entity.entity(client, MediaType.APPLICATION_JSON));

        assertEquals("should return status 201", 201, response.getStatus());

        String s1 = target.path("/client/all").request().get(String.class);
        List<Client> newClientList = JacksonUtils.readValues(s1, Client.class);
    }

    @Test
    public void update() {
        Client updClient = Client.builder()
                .id(CLIENT_1_ID)
                .name("new name")
                .email("new@mail.com")
                .build();

        Response response = target.path("/client/update").request()
                .post(Entity.entity(updClient, MediaType.APPLICATION_JSON));

        assertEquals("should return status 201", 202, response.getStatus());

        String s = target.path("/client/" + CLIENT_1_ID).request().get(String.class);
        Client client = JacksonUtils.readValue(s, Client.class);

        CLIENTS_MATCHER.assertMatch(updClient, client);
    }

    //100000
    @Test
    public void delete() {
        String s = target.path("/client/all").request().get(String.class);
        List<Client> oldClientList = JacksonUtils.readValues(s, Client.class);

        String s1 = target.path("/client/delete/" + CLIENT_1_ID).request().get(String.class);
        Assert.assertEquals(s1, String.valueOf(CLIENT_1_ID));

        String s2 = target.path("/client/all").request().get(String.class);
        List<Client> newClientList = JacksonUtils.readValues(s2, Client.class);

        oldClientList.removeAll(newClientList);

        assertEquals(1, oldClientList.size());
    }
}