package ru.otus.slisenko.orm.executor;

import ru.otus.slisenko.orm.dataset.DataSet;

import java.lang.reflect.Field;
import java.sql.*;

public class ExecutorImp implements Executor {
    private static final String CREATE_TABLE = "CREATE TABLE users (" +
            "id BIGSERIAL PRIMARY KEY," +
            "name VARCHAR(255)," +
            "age SMALLINT CHECK(age > 0 AND age < 150));";
    private static final String INSERT_USER = "INSERT INTO users (name, age) VALUES (?,?);";
    private static final String SELECT_USER = "SELECT * FROM users WHERE id = ?;";
    private static final String DROP_TABLE = "DROP TABLE users;";
    private final Connection connection;

    public ExecutorImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void prepareTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        }
    }

    @Override
    public <T extends DataSet> void save(T user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            for (Field field : user.getClass().getDeclaredFields()) {
                String name = field.getName();
                Object value = ReflectionHelper.getFieldValue(user, name);
                save(name, value, statement);
            }
            statement.executeUpdate();
        }
    }

    private void save(String fieldName, Object fieldValue, PreparedStatement statement) throws SQLException {
        switch (fieldName) {
            case "name":
                statement.setObject(1, fieldValue);
                break;
            case "age":
                statement.setObject(2, fieldValue);
                break;
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> type) throws SQLException {
        T user;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            user = ReflectionHelper.instantiate(type);
            if (user != null) {
                user.setId(id);
                while (resultSet.next())
                    for (Field field : user.getClass().getDeclaredFields()) {
                        String name = field.getName();
                        load(name, user, resultSet);
                    }
            }
        }
        return user;
    }

    private void load(String fieldName, Object user, ResultSet resultSet) throws SQLException {
        switch (fieldName) {
            case "name":
                ReflectionHelper.setFieldValue(user, fieldName, resultSet.getObject("name"));
                break;
            case "age":
                ReflectionHelper.setFieldValue(user, fieldName, resultSet.getObject("age"));
                break;
        }
    }

    @Override
    public void deleteTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        }
    }
}
