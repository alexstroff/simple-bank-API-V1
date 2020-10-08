package com.bank.service;

public interface BaseService<T> {

    T getById(int id);

    T add(T t);

    T update(T t);

    boolean delete(int id);
}
