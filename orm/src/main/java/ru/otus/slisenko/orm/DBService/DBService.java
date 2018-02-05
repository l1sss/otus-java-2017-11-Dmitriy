package ru.otus.slisenko.orm.DBService;

import ru.otus.slisenko.orm.dataset.DataSet;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {

    String getMetaData() throws SQLException;

    void prepareTable() throws SQLException;

    <T extends DataSet> void addUser(T user) throws SQLException;

    <T extends DataSet> T getUser(long id, Class<T> type) throws SQLException;

    void deleteTable() throws SQLException;
}
