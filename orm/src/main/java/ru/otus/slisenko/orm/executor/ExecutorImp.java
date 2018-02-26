package ru.otus.slisenko.orm.executor;

import java.sql.*;

public class ExecutorImp implements Executor {
    final Connection connection;

    public ExecutorImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int execUpdate(String update) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(update);
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long execInsert(String update) {
        try (PreparedStatement statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
