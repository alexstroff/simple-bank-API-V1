package com.bank.service;

import com.bank.model.Client;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientRepository repository;
    private TxManager txManager;

    public ClientService() {
        this.repository = new ClientRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    public Client getById(Integer id) {
        Client client = null;
        try {
            client = txManager.doInTransaction(() -> repository.getClientById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }


    public List<Client> getAll() {
        List<Client> clientList = null;
        try {
            clientList = txManager.doInTransaction(() -> repository.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientList;
    }

    public Client addNewClient(Client client) {
        Client newClient = null;
        try {
            newClient = txManager.doInTransaction(() -> repository.save(client));
            if (client == null) {
                throw new SQLException("Client was not created!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newClient;
    }

    public Client updateClient(Client client) {
        try {
            Client finalClient = client;
            client = txManager.doInTransaction(() -> repository.save(finalClient));
            if (client == null) {
                throw new SQLException("Client was not updated!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public boolean deleteClient(Integer id) {
        boolean success = false;
        try {
            success = txManager.doInTransaction(() -> repository.deleteClient(id));
            if (!success) {
                throw new SQLException("Client was not deleted!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

}
