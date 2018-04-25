package ru.otus.slisenko.dbservice.executor;

public interface Executor {

    long execInsert(String insert);

    int execUpdate(String update);

    <T> T execQuery(String query, ResultHandler<T> handler);
}
