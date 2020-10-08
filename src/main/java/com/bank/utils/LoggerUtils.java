package com.bank.utils;

import java.lang.invoke.MethodHandles;

public class LoggerUtils {

    public static String getClassName() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            return e.getStackTrace()[1].getClassName();
        }

//        return MethodHandles.lookup().lookupClass();
    }
}
