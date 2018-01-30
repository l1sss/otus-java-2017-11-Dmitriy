package ru.otus.slisenko.json_object_writer.adapter;

import ru.otus.slisenko.json_object_writer.JsonWriter;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import java.lang.reflect.Field;

public class ObjectAdapter extends TypeAdapter {
    @Override
    public JsonStructure addToJsonTree(JsonWriter writer, Object source) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (source != null) {
            for (Field field : source.getClass().getDeclaredFields()) {
                String name = field.getName();
                Object value = ReflectionHelper.getFieldValue(source, name);
                if (value != null) {
                    Class<?> type = field.getType();
                    if (type.isPrimitive())
                        addPrimitiveToJsonObject(name, value, builder);
                    else if (String.class.isAssignableFrom(type))
                        builder.add(name, value.toString());
                    else
                        builder.add(name, writer.toJsonTree(value));
                }
            }
        }
        return builder.build();
    }
}
