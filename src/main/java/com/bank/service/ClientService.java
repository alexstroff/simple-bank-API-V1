package com.bank.service;

import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {

    /**
     * Get Entity by Id.
     *
     * @param id
     * @return Client.
     * @throws SQLException
     */
    Client getById(int id);

    /**
     * Get all clients in Data Base
     *
     * @return List<Client>
     */
    List<Client> getAll();

    /**
     * Persist Entity.
     *
     * @param client
     * @return client
     * @throws SQLException if not found or not created
     */
    Client save(Client client);

    /**
     * Delete Entity by Id.
     *
     * @param id
     * @return true == "success"
     * @throws SQLException
     */
    boolean delete(int id);


}
