package ru.otus.slisenko.webserver.orm.dbservices;

import org.postgresql.Driver;
import ru.otus.slisenko.webserver.util.PropertiesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionHelper {
    private static final String JDBC_PROPERTIES_NAME = "cfg/jdbc.properties";

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            Properties properties = PropertiesHelper.getProperties(JDBC_PROPERTIES_NAME);
            String url = properties.getProperty("jdbc.connection.url");
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
