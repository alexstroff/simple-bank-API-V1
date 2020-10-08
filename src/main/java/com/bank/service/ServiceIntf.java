package com.bank.service;


import java.util.List;

interface ServiceIntf<T>{

     T getById(int id);

     List<T> getAll(int id);

     void add(Object o, Object b);

     void add(Object o);

     void update(Object o);

     boolean delete(Object o);




}
