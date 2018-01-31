package ru.otus.slisenko.json_object_writer.adapter;

import ru.otus.slisenko.json_object_writer.JsonWriter;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import java.util.Arrays;

public abstract class TypeAdapter {
    private static Class<?>[] SIMPLE_TYPES = {
            Byte.class, Short.class, Integer.class, Long.class, Float.class,
            Double.class, Boolean.class, Character.class, String.class
    };

    public abstract JsonStructure addToJsonTree(JsonWriter writer, Object source);

    boolean isSimpleType(Class<?> type) {
        return Arrays.stream(SIMPLE_TYPES)
                .anyMatch((simpleType) -> simpleType.isAssignableFrom(type));
    }

    void addSimpleValueToJsonArray(Object arrayValue, JsonArrayBuilder builder) {
        if (arrayValue instanceof Byte)
            builder.add((Byte) arrayValue);
        else if (arrayValue instanceof Short)
            builder.add((Short) arrayValue);
        else if (arrayValue instanceof Integer)
            builder.add((Integer) arrayValue);
        else if (arrayValue instanceof Long)
            builder.add((Long) arrayValue);
        else if (arrayValue instanceof Float)
            builder.add((Float) arrayValue);
        else if (arrayValue instanceof Double)
            builder.add((Double) arrayValue);
        else if (arrayValue instanceof Boolean)
            builder.add((Boolean) arrayValue);
        else if (arrayValue instanceof Character)
            builder.add((Character) arrayValue);
        else if (arrayValue instanceof String)
            builder.add((String) arrayValue);
    }

    void addPrimitiveToJsonObject(String fieldName, Object fieldValue, JsonObjectBuilder builder) {
        if (fieldValue instanceof Byte)
            builder.add(fieldName, (Byte) fieldValue);
        else if (fieldValue instanceof Short)
            builder.add(fieldName, (Short) fieldValue);
        else if (fieldValue instanceof Integer)
            builder.add(fieldName, (Integer) fieldValue);
        else if (fieldValue instanceof Long)
            builder.add(fieldName, (Long) fieldValue);
        else if (fieldValue instanceof Float)
            builder.add(fieldName, (Float) fieldValue);
        else if (fieldValue instanceof Double)
            builder.add(fieldName, (Double) fieldValue);
        else if (fieldValue instanceof Boolean)
            builder.add(fieldName, (Boolean) fieldValue);
        else if (fieldValue instanceof Character)
            builder.add(fieldName, fieldValue.toString());
    }
}
