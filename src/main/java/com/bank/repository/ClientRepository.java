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
     * @throws SQLException
     */
    Client getClientById(Integer id) throws SQLException;

    /**
     * Returns all Clients.
     *
     * @return List<Client>
     * @throws SQLException
     */
    List<Client> getAll() throws SQLException;

    /**
     * Save client.
     *
     * @param client
     * @return client
     * @throws SQLException if not found or not created
     */
    Client save(Client client) throws SQLException;

    /**
     * Delete Client by Id.
     *
     * @param id
     * @return true == "success"
     * @throws SQLException
     */
    boolean deleteClient(Integer id) throws SQLException;

//    void addClientAccount(Client client, Account account) throws SQLException;


//    Client getById (int clientId) throws SQLException;
//    Client getByAccountId (int AccountId) throws SQLException;
//
//
//
//    boolean add(String name, String email) throws SQLException;
}
