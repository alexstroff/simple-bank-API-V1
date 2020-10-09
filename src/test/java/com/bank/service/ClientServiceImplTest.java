package com.bank.service;

import com.bank.model.Client;
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
import java.util.List;

import static com.bank.ClientTestData.*;

public class ClientServiceImplTest {

    private static ClientService service;

    @BeforeClass
    public static void setup() {
        service = new ClientServiceImpl();
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
        Client client;
        client = service.getById(CLIENT_1_ID);
        CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
    }

    @Test
    public void getAll() {
        List<Client> allClients = service.getAll();
        Assert.assertEquals(allClients.size(), 2);
        CLIENTS_MATCHER.assertMatch(allClients, CLIENTS);
    }

    @Test
    public void add() {
        Client newClient = Client.builder()
                .name(CLIENT_3.getName())
                .email(CLIENT_3.getEmail())
                .build();
        service.save(newClient);
        Client client1 = service.getById(CLIENT_3.getId());
        CLIENTS_MATCHER.assertMatch(newClient, client1);

    }

    @Test
    public void update() {
        Client client = Client.builder()
                .id(CLIENT_1_ID)
                .name("update name")
                .email("update@mail.ru")
                .build();
        service.save(client);
        Client client1 = service.getById(CLIENT_1_ID);
        CLIENTS_MATCHER.assertMatch(client, client1);
    }

    @Test
    public void delete() {
        Client client = Client.builder()
                .name("update name")
                .email("update@mail.ru")
                .build();
        service.save(client);
        Assert.assertEquals(3, service.getAll().size());
        service.delete(100006);
        List<Client> clients = service.getAll();
        CLIENTS_MATCHER.assertMatch(clients, CLIENT_1, CLIENT_2);
    }
}