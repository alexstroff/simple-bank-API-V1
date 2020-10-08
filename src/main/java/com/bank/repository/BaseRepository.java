package com.bank.repository;

import java.sql.SQLException;

public interface BaseRepository<T> {
    /**
     * Get Entity by Id.
     *
     * @param id
     * @return Client.
     * @throws SQLException
     */
    T getById(int id) throws SQLException;

    /**
     * Persist Entity.
     *
     * @param t
     * @return client
     * @throws SQLException if not found or not created
     */
    T save(T t) throws SQLException;

    /**
     * Delete Entity by Id.
     *
     * @param id
     * @return true == "success"
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;

}
