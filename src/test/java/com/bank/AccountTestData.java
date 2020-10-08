package com.bank;

import com.bank.model.Account;

import java.math.BigDecimal;

import static com.bank.ClientTestData.*;

public class AccountTestData {
    public static TestMatcher<Account> ACCOUNT_MATCHER_WITHOUT_CLIENT =
            TestMatcher.usingFieldsComparator(Account.class, "client", "amount");

    public static final int ACCOUNT_1_ID = 100002;

    public static final Account ACCOUNT_1 =
            new Account(ACCOUNT_1_ID, CLIENT_1, "1111111111", new BigDecimal(1000).setScale(2), "RUB");

}
