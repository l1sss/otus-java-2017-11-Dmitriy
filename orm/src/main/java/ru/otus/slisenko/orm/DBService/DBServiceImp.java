package ru.otus.slisenko.orm.DBService;

import ru.otus.slisenko.orm.dataset.DataSet;
import ru.otus.slisenko.orm.executor.Executor;
import ru.otus.slisenko.orm.executor.ExecutorImp;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImp implements DBService {
    private final Connection connection;

    public DBServiceImp() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public String getMetaData() throws SQLException {
        return "Connected to: " + connection.getMetaData().getURL() + "\n" +
                "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                "Driver: " + connection.getMetaData().getDriverName();
    }

    @Override
    public void prepareTable() throws SQLException {
        Executor executor = new ExecutorImp(connection);
        executor.prepareTable();
        System.out.println("TABLE CREATED");
    }

    @Override
    public <T extends DataSet> void addUser(T user) throws SQLException {
        Executor executor = new ExecutorImp(connection);
        executor.save(user);
        System.out.println("USER SAVED");
    }

    @Override
    public <T extends DataSet> T getUser(long id, Class<T> type) throws SQLException {
        Executor executor = new ExecutorImp(connection);
        return executor.load(id, type);
    }

    @Override
    public void deleteTable() throws SQLException {
        Executor executor = new ExecutorImp(connection);
        executor.deleteTable();
        System.out.println("TABLE DELETED");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
