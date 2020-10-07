package com.bank;

import com.bank.model.CreditCard;

import java.util.Comparator;

public class CreditCardTestData {
    public static TestMatcher<CreditCard> CARD_MATCHER =
            TestMatcher.usingFieldsComparator(CreditCard.class, "account", "registered", "client");


    public static CreditCard CARD_1 =
            new CreditCard(100004, AccountTestData.ACCOUNT_1, "9991111111", null, ClientTestData.CLIENT_1);

    public static final Comparator<CreditCard> creditCardComparator = new Comparator<CreditCard>() {
        @Override
        public int compare(CreditCard o1, CreditCard o2) {
            return o2.getNumber().compareTo(o1.getNumber());
        }
    };
}
