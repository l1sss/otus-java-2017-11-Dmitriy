package ru.otus.slisenko.orm.datasets;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DataSetAdapter {
    private final Map<String, Class<?>> fields = new HashMap<>();
    private String tableName;

    public DataSetAdapter(Class<? extends DataSet> type) {
        tableName = createTableModel(type);
    }

    private String createTableModel(Class<? extends DataSet> type) {
        for (Field field : type.getDeclaredFields())
            fields.put(field.getName(), field.getType());

        TableName declaredAnnotation = type.getDeclaredAnnotation(TableName.class);
        if (declaredAnnotation == null)
            throw new RuntimeException("Undefined table name for " + type.getSimpleName() + "!");

        return declaredAnnotation.name();
    }

    public Map<String, Class<?>> getFields() {
        return fields;
    }

    public String getTableName() {
        return tableName;
    }
}
