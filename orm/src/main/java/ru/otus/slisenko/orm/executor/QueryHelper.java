package ru.otus.slisenko.orm.executor;

import ru.otus.slisenko.orm.datasets.DataSet;
import ru.otus.slisenko.orm.datasets.DataSetAdapter;

import java.util.Map;

class QueryHelper {
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) values (%s);";
    private static final String SELECT_QUERY = "SELECT * FROM %s WHERE id=%s;";

    static String getSelectQuery(String tableName, long id) {
        return String.format(SELECT_QUERY, tableName, id);
    }

    static <T extends DataSet> String getInsertQuery(DataSetAdapter dataSetAdapter, T dataSet) {
        StringBuilder fieldNamesBuilder = new StringBuilder();
        StringBuilder fieldValuesBuilder = new StringBuilder();
        Map<String, Class<?>> fields = dataSetAdapter.getFields();

        int fieldsCount = 0;
        for (Map.Entry<String, Class<?>> entry : fields.entrySet()) {
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
        return String.format(INSERT_QUERY,
                dataSetAdapter.getTableName(),
                fieldNamesBuilder.toString(),
                fieldValuesBuilder.toString());
    }
}
