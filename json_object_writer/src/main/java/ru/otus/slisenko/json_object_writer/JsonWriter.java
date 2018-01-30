package ru.otus.slisenko.json_object_writer;

import ru.otus.slisenko.json_object_writer.adapter.ArrayAdapter;
import ru.otus.slisenko.json_object_writer.adapter.CollectionAdapter;
import ru.otus.slisenko.json_object_writer.adapter.ObjectAdapter;
import ru.otus.slisenko.json_object_writer.adapter.TypeAdapter;

import javax.json.Json;
import javax.json.JsonStructure;
import java.io.StringWriter;
import java.util.Collection;

public class JsonWriter {
    private static final String NULL = "null";

    public String write(Object source) {
        if (source == null)
            return NULL;

        JsonStructure structure = toJsonTree(source);
        StringWriter stringWriter = new StringWriter();
        Json.createWriter(stringWriter).write(structure);
        return stringWriter.toString();
    }

    public JsonStructure toJsonTree(Object source) {
        TypeAdapter adapter = getAdapter(source.getClass());
        return adapter.addToJsonTree(this, source);
    }

    private TypeAdapter getAdapter(Class<?> type) {
        if (type.isArray())
            return new ArrayAdapter();
        else if (Collection.class.isAssignableFrom(type))
            return new CollectionAdapter();
        else
            return new ObjectAdapter();
    }
}
