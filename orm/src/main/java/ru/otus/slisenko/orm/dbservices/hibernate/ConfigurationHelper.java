package ru.otus.slisenko.orm.dbservices.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.otus.slisenko.orm.datasets.AddressDataSet;
import ru.otus.slisenko.orm.datasets.PhoneDataSet;
import ru.otus.slisenko.orm.datasets.UserDataSet;

class ConfigurationHelper {

    static Configuration getDefaultConfig() {
        Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty(Environment.DRIVER, "org.postgresql.Driver");
        configuration.setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/otus_database");
        configuration.setProperty(Environment.USER, "l1s");
        configuration.setProperty(Environment.PASS, "qwerty");
        configuration.setProperty(Environment.SHOW_SQL, "true");
        configuration.setProperty(Environment.HBM2DDL_AUTO, "create");
        configuration.setProperty(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");
        configuration.setProperty(Environment.JDBC_TIME_ZONE, "UTC");
        configuration.setProperty("hibernate.connection.useSSL", "false");

        return configuration;
    }
}
