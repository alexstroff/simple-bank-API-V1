package com.bank.rest.JacksonUtils;

import com.bank.model.Client;
import org.junit.Test;

import java.util.List;

import static com.bank.ClientTestData.*;

public class JacksonUtilsTest {

    @Test
    public void readWriteValue() throws Exception {
        String json = JacksonUtils.writeValue(CLIENT_1);
        System.out.println(json);
        Client client = JacksonUtils.readValue(json, Client.class);
        CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
    }

    @Test
    public void readWriteValues() throws Exception {
        String json = JacksonUtils.writeValue(CLIENTS);
        System.out.println(json);
        List<Client> meals = JacksonUtils.readValues(json, Client.class);
        CLIENTS_MATCHER.assertMatch(meals, CLIENTS);
    }

}