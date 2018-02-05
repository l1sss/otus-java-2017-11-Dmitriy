package ru.otus.slisenko.orm.executor;

import ru.otus.slisenko.orm.dataset.DataSet;

import java.sql.SQLException;

public interface Executor {

    <T extends DataSet> void save(T user) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    void prepareTable() throws SQLException;

    void deleteTable() throws SQLException;
}
