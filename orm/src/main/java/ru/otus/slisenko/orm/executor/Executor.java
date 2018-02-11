package ru.otus.slisenko.orm.executor;

import ru.otus.slisenko.orm.datasets.DataSet;
import ru.otus.slisenko.orm.datasets.DataSetAdapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class Executor extends BaseExecutor {
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) values (%s);";
    private static final String SELECT_QUERY = "SELECT * FROM %s WHERE id=%s;";

    public Executor(Connection connection) {
        super(connection);
    }

    public <T extends DataSet> void save(T dataSet) {
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
        execUpdate(String.format(INSERT_QUERY,
                dataSetAdapter.getTableName(),
                fieldNamesBuilder.toString(),
                fieldValuesBuilder.toString()));
    }

    public <T extends DataSet> T load(long id, Class<T> type) {
        DataSetAdapter dataSetAdapter = new DataSetAdapter(type);

        ResultHandler<T> handler = resultSet -> {
            if (resultSet.next()) {
                T instance = ReflectionHelper.instantiate(type);
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
        return execQuery(String.format(SELECT_QUERY, dataSetAdapter.getTableName(), id), handler);
    }
}
