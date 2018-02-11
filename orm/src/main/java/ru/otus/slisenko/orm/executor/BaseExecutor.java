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

    int execUpdate(String update) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(update);
            return statement.getUpdateCount();
        }
    }

    <T extends DataSet> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        }
    }
}
