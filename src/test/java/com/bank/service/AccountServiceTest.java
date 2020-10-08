package com.bank.service;

import com.bank.AccountTestData;
import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;
import com.bank.repository.utils.DBUtils;
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

import static com.bank.AccountTestData.ACCOUNT_1;
import static com.bank.AccountTestData.ACCOUNT_MATCHER_WITHOUT_CLIENT;

public class AccountServiceTest {
    private static AccountService service;

    @BeforeClass
    public static void setup() {
        service = new AccountService();
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
    public void getAll() {
//            Vasay, vasyaTheGreat@mail.ru, //100_000
//            100000, 1111111111, 1000, RUB, //100_002
        Client client = Client.builder().id(100000).name("Vasay").email("vasyaTheGreat@mail.ru").build();
        Account account = Account.builder().id(100002).number("1111111111").amount(new BigDecimal(1000).setScale(2)).currency("RUB").build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        List<Account> accounts1 = service.getAll(client);
        AccountTestData.ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(accounts, accounts1);

    }

    @Test
    public void getById() {
        Account account = Account.builder().id(100002).number("1111111111").amount(new BigDecimal(1000).setScale(1)).currency("RUB").build();
        Account account1 = service.getById(account.getId());
        Assert.assertEquals(account, account1);
    }

    @Test
    public void addNew() {
        Account account = Account.builder().id(100006).number("1111111111").amount(new BigDecimal(1000).setScale(1)).currency("RUB").build();
        service.add(100000, account);
        Account account1 = service.getById(100006);
        Assert.assertEquals(account,account1);
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
        List<Account> accounts = service.getAll(client);
        service.delete(account);
        List<Account> accounts1 = service.getAll(client);
        Assert.assertNotEquals(accounts, account);

    }

}