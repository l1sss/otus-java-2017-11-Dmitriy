package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.UserDataSet;

public interface DBService extends AutoCloseable {

    void save(UserDataSet dataSet);

    UserDataSet read(long id);
}
