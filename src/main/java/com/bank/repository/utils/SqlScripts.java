package com.bank.repository.utils;


/**
 * SQL скрипты с указанием имени файла
 */
public enum SqlScripts {

    SAVE_CLIENT("dataBase/sql/query/SaveClient.sql"),

    SELECT_ALL_CLIENTS("dataBase/sql/query/SelectAllClients.sql"),

    UPDATE_CLIENT("dataBase/sql/query/UpdateClient.sql"),

    GET_CLIENT_BY_ID("dataBase/sql/query/GetClientByID.sql"),

    DELETE_CLIENT("dataBase/sql/query/DeleteClient.sql"),

    GET_ALL_CLIENT_ACCOUNTS("dataBase/sql/query/GetAllClientIAccounts.sql"),

    GET_ACCOUNT_BY_ID("dataBase/sql/query/GetAccountById.sql"),

    GET_ACCOUNT_BY_ID_AND_CLIENT_ID("dataBase/sql/query/GetAccountByIdAndClientId.sql"),

    ADD_ACCOUNT("dataBase/sql/query/AddAccount.sql"),

    UPDATE_ACCOUNT("dataBase/sql/query/UpdateAccount.sql"),

    DELETE_ACCOUNT("dataBase/sql/query/DeleteAccount.sql"),

    DELETE_ACCOUNT_BY_ID_AND_CLIENT_ID("dataBase/sql/query/DeleteAccountByIdAndClientId.sql"),

    DELETE_CARD_BY_ACCOUNT_ID_AND_CLIENT_ID("dataBase/sql/query/DeleteCardByAccountIdAndClientId.sql"),

    ADD_CARD("dataBase/sql/query/AddCard.sql"),

    GET_ALL_CARDS_BY_ACCOUNT_ID("dataBase/sql/query/GetAllCards.sql"),

    GET_ALL_CARDS_BY_CLIENT_ID_ACCOUNT_ID("dataBase/sql/query/GetAllCardsByClientIdAndAccountId.sql"),

    GET_CARD_BY_ID("dataBase/sql/query/GetCardById.sql"),

    GET_CLIENT_ID_BY_CARD_ID("dataBase/sql/query/GetClientIdByAccountId.sql"),

    FIND_CARD_BY_CLIENT_ID_ACCOUNT_ID_CARD_ID("dataBase/sql/query/GetCardByClientIdAndAccountIdAndCardId.sql"),

    UPDATE_CARD("dataBase/sql/query/UpdateCard.sql"),

    DELETE_CARD("dataBase/sql/query/DeleteCard.sql");

    private final String path;

    SqlScripts(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
