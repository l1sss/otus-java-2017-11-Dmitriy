package ru.otus.slisenko.orm.dbservices.dao;

import ru.otus.slisenko.orm.datasets.DataSetAdapter;
import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.executor.Executor;
import ru.otus.slisenko.orm.executor.ExecutorImp;
import ru.otus.slisenko.orm.executor.ResultHandler;

import java.sql.Connection;
import java.util.Map;

public class UserDataSetDAO {
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) values (%s);";
    private static final String SELECT_QUERY = "SELECT * FROM %s WHERE id=%s;";
    private final Executor executor;

    public UserDataSetDAO(Connection connection) {
        executor = new ExecutorImp(connection);
    }

    public void save(UserDataSet dataSet) {
        StringBuilder fieldNamesBuilder = new StringBuilder();
        StringBuilder fieldValuesBuilder = new StringBuilder();
        DataSetAdapter dataSetAdapter = new DataSetAdapter(dataSet.getClass());
        Map<String, Class<?>> fields = dataSetAdapter.getFields();

        int fieldsCount = 0;
        for(Map.Entry<String, Class<?>> entry : fields.entrySet()) {
            fieldsCount++;
            fieldNamesBuilder.append(entry.getKey());
            Object fieldValue = ReflectionHelper.getFieldValue(dataSet, entry.getKey());

            if (String.class.isAssignableFrom(entry.getValue()))
                fieldValuesBuilder.append("\'").append(fieldValue).append("\'");
            else
                fieldValuesBuilder.append(fieldValue);

            if (fieldsCount < fields.size()) {
                fieldNamesBuilder.append(',');
                fieldValuesBuilder.append(',');
            }
        }
        executor.execUpdate(String.format(INSERT_QUERY,
                dataSetAdapter.getTableName(),
                fieldNamesBuilder.toString(),
                fieldValuesBuilder.toString()));
    }

    public UserDataSet read(long id) {
        DataSetAdapter dataSetAdapter = new DataSetAdapter(UserDataSet.class);

        ResultHandler<UserDataSet> handler = resultSet -> {
            if (resultSet.next()) {
                UserDataSet instance = ReflectionHelper.instantiate(UserDataSet.class);
                if (instance != null) {
                    instance.setId(resultSet.getLong("id"));
                    for (Map.Entry<String, Class<?>> field : dataSetAdapter.getFields().entrySet()) {
                        ReflectionHelper.setFieldValue(instance, field.getKey(), resultSet.getObject(field.getKey()));
                    }
                }
                return instance;
            }
            return null;
        };
        return executor.execQuery(String.format(SELECT_QUERY, dataSetAdapter.getTableName(), id), handler);
    }
}
