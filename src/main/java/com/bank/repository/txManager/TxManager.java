package com.bank.repository.txManager;

import java.util.concurrent.Callable;

public interface TxManager {

    /**
     * Runs Callable in one transaction.
     *
     * @param unitOfWork
     * @param <T>
     * @return unitOfWork
     * @throws Exception
     */
    <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;

}
