package ru.otus.slisenko.dbservice;

import ru.otus.slisenko.message_server.util.PropertiesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHelper {
    private static final String JDBC_PROPERTIES_NAME = "dbservice.properties";

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Properties properties = PropertiesHelper.getProperties(JDBC_PROPERTIES_NAME);

            String url = properties.getProperty("jdbc.url") +
                    "?user=" + properties.getProperty("jdbc.user") +
                    "&password=" + properties.getProperty("jdbc.password") +
                    "&useSSL=" + properties.getProperty("jdbc.useSSL") +
                    "&serverTimezone=" + properties.getProperty("jdbc.serverTimezone");

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
