package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Client;

import java.util.List;

interface ServiceInterface<T>{


     List<T> getAll(Object o);

     T getById(Object o);

     void update(Object o);

     boolean delete(Object o);

     void add(Object o, Object b);

     void add(Object o);


}
