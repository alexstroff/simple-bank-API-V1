package com.bank.repository;

import com.bank.model.CreditCard;
import com.bank.repository.utils.SqlScripts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.*;

public class CreditCardRepositoryImpl implements CreditCardRepository {

    @Override
    public List<CreditCard> getAllCards(int accountId) throws SQLException {
        String sql = getSQLPath(SqlScripts.FIND_ALL_CARDS_BY_ACCOUNT_ID.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
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

    @Override
    public CreditCard getById(int id) throws SQLException {
        String sql = getSQLPath(SqlScripts.FIND_CARD_BY_ID.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmp = connection.prepareStatement(sql)) {
            stmp.setInt(1, id);
            ResultSet rs = stmp.executeQuery();
            if (rs.next()) {
                CreditCard card1 = CreditCard.builder()
                        .id(rs.getInt(1))
                        .number(rs.getString(2))
                        .registered(rs.getDate(3))
                        .build();
                return card1;
            }
        }
        return CreditCard.builder().id(-1).build();
    }


    @Override
    public CreditCard save(CreditCard creditCard) throws SQLException {
        String sql;
        if (creditCard.getId() < 1) {
            //INSERT INTO credit_cards (account_id, number) VALUES (? , ?)
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

//INSERT INTO credit_cards (account_id, number) VALUES (? , ?)
//    @Override
//    public CreditCard addCard(int accountId, CreditCard card) throws SQLException {
//        String sql = getSQLPath(SqlScripts.ADD_CARD.getPath());
//        try (Connection connection = getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, accountId);
//            stmt.setString(2, card.getNumber());
//            stmt.execute();
//        }
//        return new CreditCard();
//    }

//UPDATE credit_cards SET number = ? WHERE id = ?
    @Override
    public void updateCard(CreditCard card) throws SQLException {
        String sql = getSQLPath(SqlScripts.UPDATE_CARD.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, card.getNumber());
            stmt.setInt(2, card.getId());
            stmt.execute();
        }
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


    @Override
    public List<CreditCard> getAllCards(int clientId, int accountId) throws SQLException {
        String sql = getSQLPath(SqlScripts.FIND_ALL_CARDS_BY_ACCOUNT_ID.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
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




}
