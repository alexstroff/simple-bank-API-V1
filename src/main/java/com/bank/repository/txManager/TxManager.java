package com.bank.repository.txManager;

import java.util.concurrent.Callable;

public interface TxManager {

    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;

}
