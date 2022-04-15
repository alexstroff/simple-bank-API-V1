package com.bank;

import com.bank.repository.utils.DBUtils;
import com.bank.utils.HttpServerUtils;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     *
     * @param args - scripts
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            for (String arg : args) {
                RunScript.execute(DBUtils.getConnection(), new FileReader(arg));
            }
        } else {
            RunScript.execute(DBUtils.getConnection(), new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(DBUtils.getConnection(), new FileReader("src/main/resources/dataBase/H2populate.SQL"));
//            RunScript.execute(DBUtils.getConnection(), new FileReader("resources/dataBase/H2init.SQL"));
//            RunScript.execute(DBUtils.getConnection(), new FileReader("resources/dataBase/H2populate.SQL"));
            System.out.println("Starting server with example scripts");
        }
        HttpServerUtils.startService();
    }
//
    //HELLO from alex
}

