package ru.otus.slisenko.json_object_writer.adapter;

import ru.otus.slisenko.json_object_writer.JsonWriter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;

public class CollectionAdapter extends TypeAdapter {
    @Override
    public JsonStructure addToJsonTree(JsonWriter writer, Object collection) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Object value : (Iterable<?>) collection) {
            if (value == null)
                builder.addNull();
            else if (isSimpleTypeValue(value.getClass()))
                addSimpleValueToJsonArray(value, builder);
            else
                builder.add(writer.toJsonTree(value));
        }
        return builder.build();
    }
}
