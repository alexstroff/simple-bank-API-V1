package com.bank;

import com.bank.repository.utils.DBUtils;
import org.h2.tools.RunScript;

import java.io.FileReader;

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
            System.out.println("Starting server without argument (scripts)");
        }
        HttpServerUtils.startService();
    }
    //HELLO from alex
}

