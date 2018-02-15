package ru.otus.slisenko.orm.dbservices.jdbc.executor;

import ru.otus.slisenko.orm.datasets.DataSet;

public interface Executor {

    int execUpdate(String update);

    <T extends DataSet> T execQuery(String query, ResultHandler<T> handler);
}
