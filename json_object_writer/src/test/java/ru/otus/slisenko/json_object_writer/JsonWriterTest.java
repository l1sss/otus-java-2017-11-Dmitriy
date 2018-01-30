package ru.otus.slisenko.json_object_writer;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.json_object_writer.classes_for_test.*;

import static org.junit.Assert.assertEquals;

public class JsonWriterTest {
    private JsonWriter jsonWriter;
    private Gson gson;

    @Before
    public void init() {
        jsonWriter = new JsonWriter();
        gson = new Gson();
    }

    @Test
    public void writeNull() {
        String expected = gson.toJson(null);
        String actual = jsonWriter.write(null);
        assertEquals(expected, actual);
    }

    @Test
    public void writeObjectWithoutFields() {
        ObjectWithoutFields obj = new ObjectWithoutFields();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }

    @Test
    public void writeObjectWithString() {
        ObjectWithString obj = new ObjectWithString();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }

    @Test
    public void writeObjectWithPrimitives() {
        ObjectWithPrimitives obj = new ObjectWithPrimitives();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }

    @Test
    public void writeObjectWithTransient() {
        ObjectWithTransient obj = new ObjectWithTransient();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }

    @Test
    public void writeObjectWithPrimitiveArray() {
        ObjectWithArray obj = new ObjectWithArray();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }

    @Test
    public void writeObjectWithIterable() {
        ObjectWithIterable obj = new ObjectWithIterable();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }

    @Test
    public void writeMixObject() {
        MixObject obj = new MixObject();
        String expected = gson.toJson(obj);
        String actual = jsonWriter.write(obj);
        assertEquals(expected, actual);
    }
}
