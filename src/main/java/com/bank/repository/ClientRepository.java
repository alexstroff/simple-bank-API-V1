package com.bank.repository;

import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    /**
     * Get Client by Id.
     *
     * @param id
     * @return Client.
     * @throws SQLException if Client with Id not found
     */
    Client getById(int id) throws SQLException;

    /**
     * Returns List of Clients.
     *
     * @return List<Client>
     * @throws SQLException if DB exceptions
     */
    List<Client> getAll() throws SQLException;

    /**
     * Persist Client.
     *
     * @param client
     * @return client
     * @throws SQLException if Client Id not found or database did not allocate a number.
     */
    Client save(Client client) throws SQLException;

    /**
     * Delete Client by Id.
     *
     * @param id
     * @return true if success.
     * @throws SQLException if Client with Id not found
     */
    boolean delete(int id) throws SQLException;

}
