package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.bank.repository.utils.DBUtils.*;

public class CreditCardRepositoryImpl implements CreditCardRepository {

    @Override
    public void addCard(int accountId, CreditCard card) throws SQLException {
        String sql = getSQLPath(SqlScripts.ADD_CARD.getPath());
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            stmt.setString(2, card.getNumber());
            stmt.execute();
        }
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
