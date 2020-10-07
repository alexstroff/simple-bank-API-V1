package com.bank.service;

import com.bank.model.Client;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientRepository repository;

    public ClientService() {
        this.repository = new ClientRepositoryImpl();
    }

    public Client getById(Integer id) {
        Client client = null;
        try {
            client = repository.getClientById(id);
            if (client == null) {
                throw new SQLException("Client with Id = " + id + ", not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> getAll() {
        List<Client> clientList = null;
        try {
            clientList = repository.getAll();
            if (clientList.isEmpty()) {
                throw new SQLException("No one Client found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    public Client addNewClient(Client client) {
        Client newClient = null;
        try {
            newClient = repository.save(client);
            if (client == null) {
                throw new SQLException("Client was not created!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newClient;
    }

    public Client updateClient(Client client) {
        try {
            client = repository.save(client);
            if (client == null) {
                throw new SQLException("Client was not updated!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public boolean deleteClient(Integer id) {
        boolean success = false;
        try {
            success = repository.deleteClient(id);
            if (!success) {
                throw new SQLException("Client was not deleted!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

}
