package ru.otus.slisenko.webserver.orm.dbservices;

import ru.otus.slisenko.webserver.orm.cache.CacheEngine;
import ru.otus.slisenko.webserver.orm.datasets.DataSet;
import ru.otus.slisenko.webserver.orm.datasets.UserDataSet;

import java.util.List;

public interface DBService extends AutoCloseable {

    void createTable();

    void deleteTable();

    List<String> getAllTables();

    void save(UserDataSet dataSet);

    UserDataSet load(long id);

    List<UserDataSet> loadAll();

    CacheEngine<Long, UserDataSet> getCache();
}
