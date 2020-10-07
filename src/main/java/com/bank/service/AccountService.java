package com.bank.service;

import com.bank.repository.AccountRepository;
import com.bank.repository.AccountRepositoryImpl;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;

import javax.sql.DataSource;
import java.util.Random;

public class AccountService {

    private final AccountRepository repository;
    private final ClientRepository clientRepository;

    public AccountService(DataSource dataSource) {
        this.repository = new AccountRepositoryImpl();
        this.clientRepository = new ClientRepositoryImpl();
    }

//    //    Проверка баланса
//    public BigDecimal checkBalance(Account account) {
//        try {
//            return repository.checkBalanceByAccountId(account.getId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void depositeFunds(Account account, BigDecimal amount) {
//        try {
//            boolean success = repository.depositFunds(account.getNumber(), amount);
//            if (!success) {
//                throw new SQLException("Exception! Funds have not been made!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<CreditCard> getListOfCreditCards(Client client) {
//        try {
//            List<Account> accountList = repository.getAccountListByClientId(client.getId());
//            if (accountList.size() < 1) {
//                throw new SQLException("For Client with Id = " + client.getId() + ", no cards was found!");
//            }
//
//            List<CreditCard> cardList = new ArrayList<>();
//            for (Account account : accountList) {
//                List<CreditCard> cards = repository.getCreditCardListByAccountId(account.getId());
//                if (!cards.isEmpty()) {
//                    cardList.addAll(cards);
//                }
//            }
//
//            if (cardList.size() < 1) {
//                throw new SQLException("For Client with Id = " + client.getId() + ", no cards was found!");
//            }
//
//            return cardList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public CreditCard creditCardIssue(Account account) {
//        try {
//            String newCardNumber;
//            do {
//                newCardNumber = getCardNumber();
//            } while (repository.isCardNumberExists(newCardNumber));
//
//            List<CreditCard> oldCardList = repository.getCreditCardListByAccountId(account.getId());
//            repository.addCreditCard(account.getId(), newCardNumber);
//            List<CreditCard> newCardList = repository.getCreditCardListByAccountId(account.getId());
//            newCardList.removeAll(oldCardList);
//
//            if (newCardList.size() != 1) {
//                throw new SQLException("Exception! Too many cards added!");
//            }
//
//            CreditCard card = newCardList.get(0);
//            card.setAccount(account);
//            card.setClient(clientRepository.getByAccountId(account.getId()));
//
//            return card;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    private String getCardNumber() {
        return String.valueOf(1999999999 + new Random().nextInt(Integer.MAX_VALUE - 1999999999));
    }

//1) Выпуск новой карты по счету
//2) Проcмотр списка карт
//3) Внесение вредств
//4) Проверка баланса
}
