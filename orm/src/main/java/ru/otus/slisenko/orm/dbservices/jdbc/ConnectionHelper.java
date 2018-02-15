package ru.otus.slisenko.orm.dbservices.jdbc;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());

            String url = "jdbc:postgresql://" +
                    "localhost:" +
                    "5432/" +
                    "otus_database?" +
                    "user=l1s&" +
                    "password=qwerty&" +
                    "useSSL=false" +
                    "serverTimezone=UTC";

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
