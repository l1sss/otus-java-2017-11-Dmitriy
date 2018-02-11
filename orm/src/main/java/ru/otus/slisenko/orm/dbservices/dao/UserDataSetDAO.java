package ru.otus.slisenko.orm.dbservices.dao;

import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.executor.Executor;

import java.sql.Connection;

public class UserDataSetDAO {
    private final Executor executor;

    public UserDataSetDAO(Connection connection) {
        executor = new Executor(connection);
    }

    public void save(UserDataSet dataSet) {
        executor.save(dataSet);
    }

    public UserDataSet read(long id) {
        return executor.load(id, UserDataSet.class);
    }
}
