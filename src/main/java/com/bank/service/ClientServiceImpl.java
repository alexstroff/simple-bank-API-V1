package com.bank.service;

import com.bank.model.Client;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;
import com.bank.repository.txManager.TxManager;
import com.bank.repository.txManager.TxManagerImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository repository;
    private TxManager txManager;

    public ClientServiceImpl() {
        this.repository = new ClientRepositoryImpl();
        this.txManager = new TxManagerImpl();
    }

    @Override
    public Client getById(int id) {
        log.trace("id={}", id);
        Client client = null;
        try {
            client = txManager.doInTransaction(() -> repository.getById(id));
        } catch (Exception e) {
            log.warn("id={}", id, e);
        }
        log.trace("returning={}", client);
        return client;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = null;
        try {
            clientList = txManager.doInTransaction(() -> repository.getAll());
        } catch (Exception e) {
            log.warn("", e);
        }
        log.trace("returning={}", clientList);
        return clientList;
    }

    @Override
    public Client add(Client client) {
        log.trace("got={}", client);
        Client newClient = null;
        try {
            newClient = txManager.doInTransaction(() -> repository.save(client));
        } catch (Exception e) {
            log.warn("Client was not created!", e);
        }
        log.trace("returning={}", newClient);
        return newClient;
    }

    @Override
    public Client update(Client client) {
        log.trace("got={}", client);
        try {
            Client finalClient = client;
            client = txManager.doInTransaction(() -> repository.save(finalClient));
        } catch (Exception e) {
            log.warn("Client was not updated!", e);
        }
        log.trace("returning={}", client);
        return client;
    }

    @Override
    public boolean delete(int id) {
        boolean success = false;
        try {
            success = txManager.doInTransaction(() -> repository.delete(id));
        } catch (Exception e) {
            log.warn("Client was not updated!", e);
        }
        log.trace("returning={}", success);
        return success;
    }

}
