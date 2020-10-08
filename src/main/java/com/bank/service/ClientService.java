package com.bank.service;

import com.bank.model.Client;

import java.util.List;

public interface ClientService extends BaseService<Client> {

    List<Client> getAll();

}
