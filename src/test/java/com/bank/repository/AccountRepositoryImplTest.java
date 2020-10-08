package com.bank.repository;

import com.bank.ClientTestData;
import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.repository.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.*;

@Slf4j
public class AccountRepositoryImplTest {

    private static AccountRepository repository;

    @BeforeClass
    public static void setup() {
        repository = new AccountRepositoryImpl();
    }

    @Before
    public void setUp() {
        try (Connection connection = DBUtils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getById() throws SQLException {
        Account account = repository.getById(ACCOUNT_1_ID);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
    }

    @Test
    public void getAllAccounts() throws SQLException {
        List<Account> client1Accs = repository.getAll(CLIENT_1.getId());
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(client1Accs, Arrays.asList(ACCOUNT_1));
    }

    @Test
    public void addAccount() throws SQLException {
        Account account = Account.builder()
                .client(CLIENT_1)
                .number("40817810500550987654")
                .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                .currency("RUB")
                .build();

        List<Account> oldAccounts = repository.getAll(CLIENT_1.getId());
        log.debug("before add={}", oldAccounts);
        Assert.assertEquals(1, oldAccounts.size());

        account.setClient(Client.builder().id(CLIENT_1_ID).build());
        Account newAccount = repository.save(account);

        List<Account> newAccounts = repository.getAll(CLIENT_1.getId());
        log.debug("after add={}", newAccounts);
        Assert.assertEquals(2, newAccounts.size());

        account.setId(newAccount.getId());
        log.debug("Added account={}", newAccount);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, newAccount);
    }

    @Test
    public void updateAccount() throws SQLException {
        Account account = ACCOUNT_1;
        account.setAmount(new BigDecimal(121212).setScale(2));
        repository.save(account);
        Account account1 = repository.getById(ACCOUNT_1.getId());
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, account1);
    }

    @Test
    public void deleteAccount() throws SQLException {
        Account account = Account.builder()
                .client(ClientTestData.CLIENT_1)
                .number("40817810500550987654")
                .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                .currency("RUB")
                .build();
        account.setClient(Client.builder().id(CLIENT_1_ID).build());
        repository.save(account);
//        repository.addAccount(CLIENT_1.getId(), account);
        List<Account> oldAccounts = repository.getAll(CLIENT_1.getId());
        Assert.assertEquals(2, oldAccounts.size());

        repository.delete(ACCOUNT_1.getId());
        List<Account> newAccounts = repository.getAll(CLIENT_1.getId());
        oldAccounts.removeAll(newAccounts);
        Assert.assertEquals(1, oldAccounts.size());

    }


    public void getEmptyAccountListException() throws SQLException {
        repository.delete(ACCOUNT_1.getId());
        Assert.assertEquals(0, repository.getAll(CLIENT_1.getId()));

    }

    //    @Test
//    public void checkBalanceByAccountNumber() {
//        try {
//            BigDecimal d = repository.checkBalanceByAccountNumber(ACCOUNT_1.getNumber());
//            Assert.assertEquals(d, ACCOUNT_1.getAmount());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void checkBalanceByAccountId() {
//        try {
//            BigDecimal d = repository.checkBalanceByAccountId(ACCOUNT_1.getId());
//            Assert.assertEquals(d, ACCOUNT_1.getAmount());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void depositFunds() {
//        try {
//            BigDecimal deposite = new BigDecimal(1000).setScale(2);
//            boolean success = repository.depositFunds(ACCOUNT_1.getNumber(), deposite);
//            Assert.assertTrue(success);
//            BigDecimal d = repository.checkBalanceByAccountId(ACCOUNT_1.getId());
//            BigDecimal expected = ACCOUNT_1.getAmount().add(deposite);
//            Assert.assertEquals(expected, d);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void getAccountListByClientId() {
//        try {
//            List<Account> accountListByClientId = repository.getAccountListByClientId(ClientTestData.CLIENT_1_ID);
//            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(accountListByClientId, Arrays.asList(ACCOUNT_1));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void getCreditCardListByAccountId() {
//        try {
//            List<CreditCard> cardList = repository.getCreditCardListByAccountId(ACCOUNT_1.getId());
//            Assert.assertEquals(1, cardList.size());
//            Assert.assertEquals(cardList.get(0).getNumber(), CARD_1.getNumber());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void addCreditCard() {
//        try {
//            String newNumber = "0000000000";
//            repository.addCreditCard(ACCOUNT_1.getId(), newNumber);
//            List<CreditCard> cardList = repository.getCreditCardListByAccountId(ACCOUNT_1.getId());
//            Assert.assertEquals(2, cardList.size());
//            Collections.sort(cardList, CreditCardTestData.creditCardComparator);
//            Assert.assertEquals(newNumber, cardList.get(1).getNumber());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Test
//    public void isCardNumberExists() {
//        try {
//            Assert.assertTrue(repository.isCardNumberExists(CARD_1.getNumber()));
//            Assert.assertFalse(repository.isCardNumberExists("00000000x"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}