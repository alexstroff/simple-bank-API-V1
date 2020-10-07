package com.bank.repository;


/**
 * SQL скрипты с указанием имени файла
 */
public enum SqlScripts {

    /**
     * Выбор всех счетов клиента
     */

    SAVE_CLIENT("dataBase/sql/query/SaveClient.sql"),

    SELECT_ALL_CLIENTS("dataBase/sql/query/SelectAllClients.sql"),

    UPDATE_CLIENT("dataBase/sql/query/UpdateClient.sql"),

    GET_CLIENT_BY_ID("dataBase/sql/query/GetClientByID.sql"),

    DELETE_CLIENT("dataBase/sql/query/DeleteClient.sql"),

    GET_ALL_CLIENT_ACCOUNTS("dataBase/sql/query/GetAllClientIAccounts.sql"),

    GET_ACCOUNT_BY_ID("dataBase/sql/query/GetAccountById.sql"),

    ADD_ACCOUNT("dataBase/sql/query/AddAccount.sql"),

    UPDATE_ACCOUNT("dataBase/sql/query/UpdateAccount.sql"),

    DELETE_ACCOUNT("dataBase/sql/query/DeleteAccount.sql"),

    ADD_CARD("dataBase/sql/query/AddCard.sql"),

    FIND_ALL_CARDS_BY_ACCOUNT_ID("dataBase/sql/query/GetAllCards.sql"),

    FIND_CARD_BY_ID("dataBase/sql/query/GetCardById.sql"),

    UPDATE_CARD("dataBase/sql/query/UpdateCard.sql"),

    DELETE_CARD("dataBase/sql/query/DeleteCard.sql");

    private final String path;

    SqlScripts(String path) {
        this.path = path;
    }
    public String getPath(){return path;}
}
