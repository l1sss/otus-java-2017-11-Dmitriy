package ru.otus.slisenko.orm.executor;

import ru.otus.slisenko.orm.datasets.DataSet;
import ru.otus.slisenko.orm.datasets.DataSetAdapter;

import java.sql.*;
import java.util.Map;

public class Executor extends BaseExecutor {

    public Executor(Connection connection) {
        super(connection);
    }

    public <T extends DataSet> void save(T dataSet) throws SQLException {
        DataSetAdapter dataSetAdapter = new DataSetAdapter(dataSet.getClass());
        String insertQuery = QueryHelper.getInsertQuery(dataSetAdapter, dataSet);
        int execUpdate = execUpdate(insertQuery);
        System.out.println("Inserted " + execUpdate + " rows");
    }

    public <T extends DataSet> T load(long id, Class<T> type) throws SQLException {
        DataSetAdapter dataSetAdapter = new DataSetAdapter(type);
        String selectQuery = QueryHelper.getSelectQuery(dataSetAdapter.getTableName(), id);

        ResultHandler<T> handler = resultSet -> {
            if (resultSet.next()) {
                T instance = ReflectionHelper.instantiate(type);
                if (instance != null) {
                    instance.setId(resultSet.getLong("id"));
                    for (Map.Entry<String, Class<?>> field : dataSetAdapter.getFields().entrySet())
                        ReflectionHelper.setFieldValue(instance, field.getKey(), resultSet.getObject(field.getKey()));
                }
                return instance;
            }
            return null;
        };
        return execQuery(selectQuery, handler);
    }
}
