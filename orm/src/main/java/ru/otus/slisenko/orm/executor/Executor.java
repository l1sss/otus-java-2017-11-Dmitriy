package ru.otus.slisenko.orm.executor;

public interface Executor {

    int execUpdate(String update);

    <T> T execQuery(String query, ResultHandler<T> handler);
}
