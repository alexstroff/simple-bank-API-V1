package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.*;

public class CreditCardRepositoryImpl implements CreditCardRepository {

    @Override
    public CreditCard addCard(int accountId, CreditCard card) throws SQLException {
        String sql = getSQLPath(SqlScripts.ADD_CARD.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, accountId);
            stmt.setString(2, card.getNumber());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
        }
        return card;
    }

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
    public CreditCard getCardById(int cardId) throws SQLException {
        String sql = getSQLPath(SqlScripts.FIND_CARD_BY_ID.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmp = connection.prepareStatement(sql)) {
            stmp.setInt(1, cardId);
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
    public boolean deleteCard(int cardId) throws SQLException {
        String sql = getSQLPath(SqlScripts.DELETE_CARD.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            int rows = stmt.executeUpdate();
            if (rows != 0) return true;
        }
        return false;
    }
}
