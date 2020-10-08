package com.bank.repository;

import com.bank.model.Account;
import com.bank.repository.utils.SqlScripts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.getConnection;
import static com.bank.repository.utils.DBUtils.getSQLPath;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Account getById(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.GET_ACCOUNT_BY_ID.getPath());
        Account account = null;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = Account.builder()
                        .id(rs.getInt(1))
                        .number(rs.getString(2))
                        .amount(rs.getBigDecimal(3))
                        .currency(rs.getString(4))
                        .build();
            }
        }
        if (account == null) {
            throw new SQLException("Account with Id=" + id + ", not found");
        } else {
            return account;
        }
    }

    @Override
    public List<Account> getAll(int clientId) throws SQLException {
        String sql = getSQLPath(SqlScripts.GET_ALL_CLIENT_ACCOUNTS.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
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
    public Account save(Account account) throws SQLException {
        String sql;
        if (account.getId() < 1) {
            sql = getSQLPath(SqlScripts.ADD_ACCOUNT.getPath());
            try (Connection connection = getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, account.getClient().getId());
                ps.setString(2, account.getNumber());
                ps.setBigDecimal(3, account.getAmount());
                ps.setString(4, account.getCurrency());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        account.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating account failed, no ID obtained.");
                    }
                }
            }
        } else {
            sql = getSQLPath(SqlScripts.UPDATE_ACCOUNT.getPath());
            try (Connection connection = getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setBigDecimal(1, account.getAmount());
                ps.setInt(2, account.getId());
                int success = ps.executeUpdate();
                if (success < 1) {
                    throw new SQLException("Updating Account with Id = " + account.getId()
                            + ", was not found. Updating failed.");
                }
            }
        }
        return account;
    }


//    @Override
//    public Account addAccount(int clientId, Account account) throws SQLException {
//        String sql = getSQLPath(SqlScripts.ADD_ACCOUNT.getPath());
//
//        try (Connection connection = getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setInt(1, clientId);
//            ps.setString(2, account.getNumber());
//            ps.setBigDecimal(3, account.getAmount());
//            ps.setString(4, account.getCurrency());
//            ps.executeUpdate();
//
//            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    account.setId(generatedKeys.getInt(1));
//                } else {
//                    throw new SQLException("Creating account failed, no ID obtained.");
//                }
//            }
//        }
//        return account;
//    }

//    @Override
//    public Account updateAccount(Account account) throws SQLException {
//        String sql = getSQLPath(SqlScripts.UPDATE_ACCOUNT.getPath());
//        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(2, account.getId());
//            stmt.setBigDecimal(1, account.getAmount());
//            stmt.execute();
//        }
//        return account;
//    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.DELETE_ACCOUNT.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows != 0) return true;
        }
        return false;
    }
}
