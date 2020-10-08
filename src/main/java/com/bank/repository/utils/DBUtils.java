package com.bank.repository.utils;

import com.bank.repository.ResourceReader;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    // Base URI the Grizzly HTTP server will listen on
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:bank;"
            + "DB_CLOSE_DELAY=-1;"
            + "DATABASE_TO_UPPER=false;"
//            + "TRACE_LEVEL_FIle=4;"
//            + "TRACE_LEVEL_SYSTEM_OUT=1"
            ;

//    private static final String DB_USERNAME = "user";
//    private static final String DB_PASSWORD = "pwd";

    private static JdbcDataSource dataSource;
    private static ResourceReader resourceReader;

    static {
        try {
            Class.forName(DB_DRIVER);
            dataSource = new JdbcDataSource();
            dataSource.setURL(DB_URL);
            resourceReader = new ResourceReader();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DBUtils() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public static String getSQLPath(String path) {
        return resourceReader.getSQL(path);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
