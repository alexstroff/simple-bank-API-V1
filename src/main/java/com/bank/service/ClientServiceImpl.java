package com.bank.service;

import com.bank.model.Client;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class ClientServiceImpl implements ClientService{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ClientRepository repository;
    private TxManager txManager;

    public ClientServiceImpl() {
        this.repository = new ClientRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public Client getById(int id) {
        logger.trace("id={}", id);
        Client client = null;
        try {
            client = txManager.doInTransaction(() -> repository.getById(id));
        } catch (Exception e) {
            logger.warn("id={}", id, e);
        }
        logger.trace("returning={}", client);
        return client;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = null;
        try {
            clientList = txManager.doInTransaction(() -> repository.getAll());
        } catch (Exception e) {
            logger.warn("", e);
        }
        logger.trace("returning={}", clientList);
        return clientList;
    }

    @Override
    public Client add(Client client) {
        logger.trace("got={}", client);
        Client newClient = null;
        try {
            newClient = txManager.doInTransaction(() -> repository.save(client));
        } catch (Exception e) {
            logger.warn("Client was not created!", e);
        }
        logger.trace("returning={}", newClient);
        return newClient;
    }

    @Override
    public Client update(Client client) {
        logger.trace("got={}", client);
        try {
            Client finalClient = client;
            client = txManager.doInTransaction(() -> repository.save(finalClient));
        } catch (Exception e) {
            logger.warn("Client was not updated!", e);
        }
        logger.trace("returning={}", client);
        return client;
    }

    @Override
    public boolean delete(int id) {
        boolean success = false;
        try {
            success = txManager.doInTransaction(() -> repository.deleteClient(id));
        } catch (Exception e) {
            logger.warn("Client was not updated!", e);
        }
        logger.trace("returning={}", success);
        return success;
    }

}
