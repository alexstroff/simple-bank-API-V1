package com.bank.service;

import com.bank.AccountTestData;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.CLIENT_1;
import static com.bank.ClientTestData.CLIENT_1_ID;

@Slf4j
public class AccountServiceImplTest {
    private static AccountServiceImpl service;

    @BeforeClass
    public static void setup() {
        service = new AccountServiceImpl();
    }

    @Before
    public void setUp() throws Exception {
        try (Connection connection = DBUtils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getById() {
        Account account = service.getById(ACCOUNT_1_ID);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
    }

    @Test
    public void getAll() {
//            Vasay, vasyaTheGreat@mail.ru, //100_000
//            100000, 1111111111, 1000, RUB, //100_002
        Client client = Client.builder().id(100000).name("Vasay").email("vasyaTheGreat@mail.ru").build();
        Account account = Account.builder().id(100002).number("1111111111").amount(new BigDecimal(1000).setScale(2)).currency("RUB").build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        List<Account> accounts1 = service.getAll(client.getId());
        AccountTestData.ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(accounts, accounts1);

    }

    @Test
    public void addAccount() throws SQLException {
        Account account = Account.builder()
                .client(CLIENT_1)
                .number("40817810500550987654")
                .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                .currency("RUB")
                .build();

        List<Account> oldAccounts = service.getAll(CLIENT_1.getId());
        log.debug("before add={}", oldAccounts);
        Assert.assertEquals(1, oldAccounts.size());

        account.setClient(Client.builder().id(CLIENT_1_ID).build());
        Account newAccount = service.add(CLIENT_1_ID, account);

        List<Account> newAccounts = service.getAll(CLIENT_1.getId());
        log.debug("after add={}", newAccounts);
        Assert.assertEquals(2, newAccounts.size());

        account.setId(newAccount.getId());
        log.debug("Added account={}", newAccount);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, newAccount);
    }

    @Test
    public void update() {
        Account account = Account.builder().id(100006).number("1111111111").amount(new BigDecimal(9000)).currency("RUB").build();
        service.add(100000, account);
        account.setAmount(new BigDecimal(2000).setScale(1));
        service.update(account);
        Account account1 = service.getById(100006);
        Assert.assertEquals(account, account1);
    }

    @Test
    public void delete() {
        Client client = Client.builder().id(100000).name("Vasay").email("vasyaTheGreat@mail.ru").build();
        Account account = Account.builder().id(100002).number("1111111111").amount(new BigDecimal(1000).setScale(2)).currency("RUB").build();
        List<Account> accounts = service.getAll(client.getId());
        service.delete(account.getId());
        List<Account> accounts1 = service.getAll(client.getId());
        Assert.assertNotEquals(accounts, account);

    }

}