package ru.otus.slisenko.json_object_writer.adapter;

import ru.otus.slisenko.json_object_writer.JsonWriter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;
import java.lang.reflect.Array;

public class ArrayAdapter extends TypeAdapter {

    @Override
    public JsonStructure addToJsonTree(JsonWriter writer, Object array) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object value = Array.get(array, i);
            if (value == null)
                builder.addNull();
            else if (isSimpleType(value.getClass()))
                addSimpleValueToJsonArray(value, builder);
            else
                builder.add(writer.toJsonTree(value));
        }
        return builder.build();
    }
}
