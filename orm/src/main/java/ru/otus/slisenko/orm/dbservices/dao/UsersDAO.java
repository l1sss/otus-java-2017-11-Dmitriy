package ru.otus.slisenko.orm.dbservices.dao;

import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.executor.Executor;
import ru.otus.slisenko.orm.executor.ExecutorImp;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGSERIAL PRIMARY KEY," +
            "name VARCHAR(255)," +
            "age INTEGER CHECK(age > 0 AND age < 150));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String INSERT_USER = "INSERT INTO users(name, age) VALUES('%s', %d);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=%s;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
    private final Executor executor;

    public UsersDAO(Connection connection) {
        executor = new ExecutorImp(connection);
    }

    public void createTable() {
        executor.execUpdate(CREATE_TABLE);
    }

    public void deleteTable() {
        executor.execUpdate(DROP_TABLE);
    }

    public void save(UserDataSet dataSet) {
        String query = String.format(INSERT_USER, dataSet.getName(), dataSet.getAge());
        executor.execUpdate(query);
    }

    public UserDataSet load(long id) {
        return executor.execQuery(String.format(SELECT_USER_BY_ID, id), resultSet -> {
            resultSet.next();
            return new UserDataSet(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3));
        });
    }

    public List<UserDataSet> loadAll() {
        List<UserDataSet> users = new ArrayList<>();
        executor.execQuery(SELECT_ALL_USERS, resultSet -> {
            while(resultSet.next()) {
                users.add(new UserDataSet(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)));
            }
            return users;
        });
        return users;
    }
}
