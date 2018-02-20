package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.UserDataSet;

import java.util.List;

public interface DBService extends AutoCloseable {

    void save(UserDataSet dataSet);

    UserDataSet load(long id);

    UserDataSet loadByName(String name);

    List<UserDataSet> loadAll();
}
