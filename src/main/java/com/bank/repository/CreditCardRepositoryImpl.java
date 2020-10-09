package com.bank.repository;

import com.bank.model.CreditCard;
import com.bank.repository.utils.SqlScripts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.*;

public class CreditCardRepositoryImpl implements CreditCardRepository {

//    @Override
//    public CreditCard getById(int id) throws SQLException {
//        String sql = getSQLPath(SqlScripts.FIND_CARD_BY_ID.getPath());
//        CreditCard card = null;
//        try (Connection connection = getConnection();
//             PreparedStatement stmp = connection.prepareStatement(sql)) {
//            stmp.setInt(1, id);
//            ResultSet rs = stmp.executeQuery();
//            if (rs.next()) {
//                card = CreditCard.builder()
//                        .id(rs.getInt(1))
//                        .number(rs.getString(2))
//                        .registered(rs.getDate(3))
//                        .build();
//            }
//        }
//        if (card == null) {
//            throw new SQLException("CreditCard with Id=" + id + ", not found");
//        } else {
//            return card;
//        }
//    }

    @Override
    public CreditCard getById(int clientId, int accountId, int creditCardId) throws SQLException {
//        SELECT id, number, registered FROM credit_cards WHERE
//        (SELECT DISTINCT clients_id FROM accounts WHERE account_id = ? AND clients_id = ?) = ? AND account_id = ? AND id = ?
        String sql = getSQLPath(SqlScripts.FIND_CARD_BY_CLIENT_ID_ACCOUNT_ID_CARD_ID.getPath());
        CreditCard card = null;
        try (Connection connection = getConnection();
             PreparedStatement stmp = connection.prepareStatement(sql)) {
            stmp.setInt(1, accountId);
            stmp.setInt(2, clientId);
            stmp.setInt(3, clientId);
            stmp.setInt(4, accountId);
            stmp.setInt(5, creditCardId);
            ResultSet rs = stmp.executeQuery();
            if (rs.next()) {
                card = CreditCard.builder()
                        .id(rs.getInt(1))
                        .number(rs.getString(2))
                        .registered(rs.getDate(3))
                        .build();
            }
        }
        if (card == null) {
            throw new SQLException("CreditCard with Id=" + creditCardId + ", not found");
        } else {
            return card;
        }
    }

    @Override
    public List<CreditCard> getAll(int clientId, int accountId) throws SQLException {
        //SELECT id, number, registered FROM credit_cards WHERE account_id IN (
        // SELECT id FROM accounts WHERE clients_id = ? AND id = ?);
        String sql = getSQLPath(SqlScripts.GET_ALL_CARDS_BY_CLIENT_ID_ACCOUNT_ID.getPath());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();
            List<CreditCard> creditCards = new ArrayList<>();
            while (rs.next()) {
                CreditCard card = CreditCard.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .registered(rs.getDate("registered"))
                        .build();
                creditCards.add(card);
            }
            return creditCards;
        }
    }

    //    @Override
//    public List<CreditCard> getAll(int accountId) throws SQLException {
//        String sql = getSQLPath(SqlScripts.FIND_ALL_CARDS_BY_ACCOUNT_ID.getPath());
//        try (Connection connection = getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, accountId);
//            ResultSet rs = stmt.executeQuery();
//            List<CreditCard> creditCards = new ArrayList<>();
//            while (rs.next()) {
//                CreditCard card = CreditCard.builder()
//                        .id(rs.getInt("id"))
//                        .number(rs.getString("number"))
//                        .registered(rs.getDate("registered"))
//                        .build();
//                creditCards.add(card);
//            }
//            return creditCards;
//        }
//    }

    @Override
    public int getClientIdByAccountId(int accountId) throws SQLException {
        //SELECT TOP(1) clients_id FROM accounts WHERE id = ?
        String sql = getSQLPath(SqlScripts.GET_CLIENT_ID_BY_CARD_ID.getPath());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public CreditCard save(CreditCard creditCard) throws SQLException {
        String sql;
        if (creditCard.getId() < 1) {
            sql = getSQLPath(SqlScripts.ADD_CARD.getPath());
            try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, creditCard.getAccount().getId());
                ps.setString(2, creditCard.getNumber());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        creditCard.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Credit Card issue failed, no account ID obtained.");
                    }
                }
            }
        } else {
            sql = getSQLPath(SqlScripts.UPDATE_CARD.getPath());
            try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
                ps.setString(1, creditCard.getNumber());
                ps.setInt(2, creditCard.getId());
                int success = ps.executeUpdate();
                if (success < 1) {
                    throw new SQLException("Updating Credit card with Id = " + creditCard.getId()
                            + ", was not found. Updating failed.");
                }
            }
        }
        return creditCard;
    }

    @Override
    public boolean delete(int accountId, int cardId) throws SQLException {
        //DELETE FROM credit_cards WHERE account_id = ? AND id = ?
        String sql = getSQLPath(SqlScripts.DELETE_CARD_BY_ACCOUNT_ID_AND_CLIENT_ID.getPath());
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setInt(2, cardId);
            int rows = ps.executeUpdate();
            if (rows > 0) return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.DELETE_CARD.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) return true;
        }
        return false;
    }
}
