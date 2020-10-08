package com.bank.service;

import com.bank.model.Client;

import java.util.List;

public interface ClientService extends BaseService<Client> {

    /**
     * Get all clients in Data Base
     *
     * @return List<Client>
     */
    List<Client> getAll();

}
