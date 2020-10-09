package com.bank.service;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.CLIENT_1;
import static com.bank.ClientTestData.CLIENT_1_ID;

@Slf4j
public class AccountServiceImplTest {
    private static AccountService service;

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
        Account account = service.getById(CLIENT_1_ID, ACCOUNT_1_ID);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
    }


    @Test
    public void getAll() {
        List<Account> client1Accs = service.getAll(CLIENT_1.getId());
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(client1Accs, Arrays.asList(ACCOUNT_1));
    }

    @Test
    public void add() {
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
        Account newAccount = service.save(account);

        List<Account> newAccounts = service.getAll(CLIENT_1.getId());
        log.debug("after add={}", newAccounts);
        Assert.assertEquals(2, newAccounts.size());

        account.setId(newAccount.getId());
        log.debug("Added account={}", newAccount);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, newAccount);
    }

    @Test
    public void update() {
        Account account = new Account(ACCOUNT_1);
        account.setNumber("321543");
        Account saved = service.save(account);
        log.debug("get={}, returns={}", account, saved);
        ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(saved, account);
    }

    @Test
    public void delete() {
        Account account = Account.builder()
                .client(ClientTestData.CLIENT_1)
                .number("40817810500550987654")
                .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                .currency("RUB")
                .build();
        account.setClient(Client.builder().id(CLIENT_1_ID).build());
        service.save(account);
        List<Account> oldAccounts = service.getAll(CLIENT_1.getId());
        Assert.assertEquals(2, oldAccounts.size());
        service.delete(CLIENT_1_ID, ACCOUNT_1_ID);
        List<Account> newAccounts = service.getAll(CLIENT_1.getId());
        oldAccounts.removeAll(newAccounts);
        Assert.assertEquals(1, oldAccounts.size());
    }

}