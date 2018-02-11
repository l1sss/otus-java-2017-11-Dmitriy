package ru.otus.slisenko.orm.executor;

import ru.otus.slisenko.orm.datasets.DataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class BaseExecutor {
    final Connection connection;

    BaseExecutor(Connection connection) {
        this.connection = connection;
    }

    int execUpdate(String update) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(update);
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    <T extends DataSet> T execQuery(String query, ResultHandler<T> handler) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
