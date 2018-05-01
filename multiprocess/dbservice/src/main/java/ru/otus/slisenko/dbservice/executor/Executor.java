package ru.otus.slisenko.dbservice.executor;

public interface Executor {

    long execUpdate(String insert, ExecuteHandler prepare);

    <T> T execQuery(String query, ResultHandler<T> handler);
}
