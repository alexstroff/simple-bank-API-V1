package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.getConnection;
import static com.bank.repository.utils.DBUtils.getSQLPath;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public void addAccount(int clientId, Account account) throws SQLException {
        String sql = getSQLPath(SqlScripts.ADD_ACCOUNT.getPath());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ps.setString(2, account.getNumber());
            ps.setBigDecimal(3, account.getAmount());
            ps.setString(4, account.getCurrency());
            ps.execute();
        }
    }

//    @Override
//    public Account getAccountById(Client client) throws SQLException {
//        String sql = getSQLPath(SqlScripts.GET_ACCOUNT_BY_CLIENT_ID.getPath());
//
//        try (Connection connection = getConnection();
//        PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, client.getId());
//            ResultSet rs = stmt.executeQuery();
//            Account account = null;
//            while (rs.next()) {
//                 account = Account.builder()
//                        .id(rs.getInt("id"))
//                        .number(rs.getString("number"))
//                        .amount(rs.getBigDecimal("amount"))
//                        .currency(rs.getString("currency"))
//                        .build();
//            }
//            if(account != null){
//                return account;
//            }else {
//                throw new SQLException("Not found any account!");
//            }
//        }
//    }

    @Override
    public Account getAccountById(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.GET_ACCOUNT_BY_ID.getPath());

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt(1))
                        .number(rs.getString(2))
                        .amount(rs.getBigDecimal(3))
                        .currency(rs.getString(4))
                        .build();
                return account;
            }
        }
        return Account.builder().id(-1).build();
    }

//    @Override
//    public Account getAccountById(Account account) throws SQLException {
//        String sql = getSQLPath(SqlScripts.GET_ACCOUNT_BY_CLIENT_ID.getPath());
//
//        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, account.getId());
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                account = Account.builder()
//                        .id(rs.getInt("id"))
//                        .number(rs.getString("number"))
//                        .amount(rs.getBigDecimal("amount"))
//                        .currency(rs.getString("currency"))
//                        .build();
//            }
//            if(account != null){
//                return account;
//            }else {
//                throw new SQLException("Not found any account!");
//            }
//        }
//    }

    @Override
    public List<Account> getAllClientAccounts(int clientId) throws SQLException {
        String sql = getSQLPath(SqlScripts.GET_ALL_CLIENT_ACCOUNTS.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
//                        .client(client)
                        .number(rs.getString("number"))
                        .amount(rs.getBigDecimal("amount").setScale(2))
                        .currency(rs.getString("currency"))
                        .build();
                accounts.add(account);
            }
            return accounts;
        }
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        String sql = getSQLPath(SqlScripts.UPDATE_ACCOUNT.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(2, account.getId());
            stmt.setBigDecimal(1, account.getAmount());
            stmt.execute();
        }
    }

    @Override
    public boolean deletAccount(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.DELETE_ACCOUNT.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows != 0) return true;
        }
        return false;
    }
}
