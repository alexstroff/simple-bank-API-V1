package com.bank.repository;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;

public class Utils {
    // Base URI the Grizzly HTTP server will listen on
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:bank;"
            + "DB_CLOSE_DELAY=-1;"
            + "DATABASE_TO_UPPER=false;";
//    private static final String DB_USERNAME = "user";
//    private static final String DB_PASSWORD = "pwd";

    public static final String BASE_URI = "http://localhost:8080/";

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

    private Utils() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
//            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public static ResourceReader getResReader() {
        return resourceReader;
    }

    public static String getSQLPath(String path) {
        return resourceReader.getSQL(path);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {

        // create a resource config that scans for JAX-RS resources and providers
        // in com.novikov package
        final ResourceConfig rc = new ResourceConfig().packages("com.bank");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void startService() throws Exception {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}
