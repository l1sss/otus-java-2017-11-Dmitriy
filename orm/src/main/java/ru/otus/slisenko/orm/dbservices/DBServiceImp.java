package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.DataSet;
import ru.otus.slisenko.orm.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImp implements DBService {
    private final Connection connection;

    public DBServiceImp() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public void addDataSet(DataSet dataSet) throws SQLException {
        Executor executor = new Executor(connection);
        executor.save(dataSet);
        System.out.println("Data set's been added: " + dataSet.toString());
    }

    @Override
    public void getDataSet(long id, Class<? extends DataSet> dataSetClass) throws SQLException {
        Executor executor = new Executor(connection);
        DataSet dataSet = executor.load(id, dataSetClass);
        System.out.println(dataSet != null
                ? "Data set's been loaded: " + dataSet.toString()
                : "Loaded data set is empty");
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }
}
