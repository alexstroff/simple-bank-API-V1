package com.bank.repository;

import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository extends BaseRepository<Client> {

    /**
     * Get Entity by Id.
     *
     * @param id
     * @return Client.
     * @throws SQLException
     */
    Client getById(int id) throws SQLException;

    /**
     * Returns all Clients.
     *
     * @return List<Client>
     * @throws SQLException
     */
    List<Client> getAll() throws SQLException;

//    Client getById (int clientId) throws SQLException;
//    Client getByAccountId (int AccountId) throws SQLException;
//
//
//
//    boolean add(String name, String email) throws SQLException;
}
