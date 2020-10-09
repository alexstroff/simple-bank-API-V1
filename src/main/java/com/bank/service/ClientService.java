package com.bank.service;

import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {

    /**
     * Get Client by Id.
     *
     * @param id
     * @return Client.
     */
    Client getById(int id);

    /**
     * Get all clients in Data Base
     *
     * @return List<Client>
     */
    List<Client> getAll();

    /**
     * Persist Client.
     *
     * @param client
     * @return client
     */
    Client save(Client client);

    /**
     * Delete Client by Id.
     *
     * @param id
     * @return true if success.
     */
    boolean delete(int id);


}
