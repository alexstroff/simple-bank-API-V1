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
        Account account = repository.getById( CLIENT_1_ID, ACCOUNT_1_ID);
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

//    @Test
//    public void updateAccount() throws SQLException {
//        Account account = ACCOUNT_1;
//        account.setAmount(new BigDecimal(121212).setScale(2));
//        repository.save(account);
//        Account account1 = repository.getById(ACCOUNT_1.getId());
//        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, account1);
//    }

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
}