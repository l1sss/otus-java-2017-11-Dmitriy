package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.DataSet;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {

    void addDataSet(DataSet dataSet) throws SQLException;

    void getDataSet(long id, Class<? extends DataSet> dataSetClass) throws SQLException;
}
