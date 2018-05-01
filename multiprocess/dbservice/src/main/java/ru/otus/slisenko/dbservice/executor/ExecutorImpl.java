package ru.otus.slisenko.dbservice.executor;

import java.sql.*;

public class ExecutorImpl implements Executor {
    private final Connection connection;

    public ExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long execUpdate(String insert, ExecuteHandler prepare) {
        try (PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prepare.accept(statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
