package com.bank.repository.txManager;

import com.bank.repository.utils.DBUtils;

import java.sql.Connection;
import java.util.concurrent.Callable;

public class TxManagerImpl implements TxManager {

    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception {
        Connection connection = DBUtils.getConnection();
        T result = null;
        try {
            connection.setAutoCommit(false);
            result = unitOfWork.call();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
            return result;
    }
}
