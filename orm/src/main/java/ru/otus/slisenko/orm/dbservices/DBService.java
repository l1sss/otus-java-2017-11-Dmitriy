package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.UserDataSet;

import java.util.List;

public interface DBService extends AutoCloseable {

    void createTable();

    void deleteTable();

    List<String> getAllTables();

    void save(UserDataSet dataSet);

    UserDataSet load(long id);

    List<UserDataSet> loadAll();
}
