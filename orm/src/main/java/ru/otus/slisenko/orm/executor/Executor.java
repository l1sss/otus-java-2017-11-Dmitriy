package ru.otus.slisenko.orm.executor;

public interface Executor {

    int execUpdate(String update);

    long execInsert(String insert);

    <T> T execQuery(String query, ResultHandler<T> handler);
}
