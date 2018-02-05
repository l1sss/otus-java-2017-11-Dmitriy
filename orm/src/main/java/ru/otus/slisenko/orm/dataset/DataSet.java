package ru.otus.slisenko.orm.dataset;

public abstract class DataSet {
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public abstract String toString();
}
