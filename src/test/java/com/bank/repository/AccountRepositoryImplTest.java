package com.bank.repository;

import com.bank.ClientTestData;
import com.bank.model.Account;
import com.bank.model.Client;
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

public class AccountRepositoryImplTest {

    private static AccountRepository repository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setup() {
        repository = new AccountRepositoryImpl();
        clientRepository = new ClientRepositoryImpl();
    }

    @Before
    public void setUp() {
        try (Connection connection = Utils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAccount() throws SQLException {
            Account account = Account.builder()
                    .client(ClientTestData.CLIENT_1)
                    .number("40817810500550987654")
                    .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                    .currency("RUB")
                    .build();

            List<Account> oldAccounts = repository.getAllClientAccounts(CLIENT_1);
            repository.addAccount(CLIENT_1, account);
            List<Account> newAccounts = repository.getAllClientAccounts(CLIENT_1);
            newAccounts.removeAll(oldAccounts);
            Assert.assertEquals(1, newAccounts.size());

            Account newAccount = newAccounts.get(0);
            newAccount.setId(null);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, newAccount);
    }

    @Test
    public void getAllAccounts() throws SQLException {
            List<Account> client1Accs = repository.getAllClientAccounts(CLIENT_1);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(client1Accs, Arrays.asList(ACCOUNT_1));
    }

    @Test
    public void getAccById() throws SQLException {
            Account account = repository.getAccountById(100002);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
    }

    @Test
    public void updateAccount() throws SQLException {
            Account account = ACCOUNT_1;
            account.setAmount(new BigDecimal(121212).setScale(2));
            repository.updateAccount(account);
            Account account1 = repository.getAccountById(ACCOUNT_1.getId());
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
        repository.addAccount(CLIENT_1, account);
        List<Account> oldAccounts = repository.getAllClientAccounts(CLIENT_1);
        Assert.assertEquals(2, oldAccounts.size());

        repository.deletAccount(ACCOUNT_1);
        List<Account> newAccounts = repository.getAllClientAccounts(CLIENT_1);
        oldAccounts.removeAll(newAccounts);
        Assert.assertEquals(1, oldAccounts.size());

    }

    @Test(expected = SQLException.class)
    public void getEmptyAccountListException() throws SQLException {
        repository.deletAccount(ACCOUNT_1);
        repository.getAllClientAccounts(CLIENT_1);

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