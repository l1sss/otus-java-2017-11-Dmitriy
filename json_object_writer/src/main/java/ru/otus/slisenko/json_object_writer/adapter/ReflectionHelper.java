package ru.otus.slisenko.json_object_writer.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class ReflectionHelper {
    private ReflectionHelper() {
    }

    static Object getFieldValue(Object source, String name) {
        Field field = null;
        boolean isAccessable = true;
        try {
            field = source.getClass().getDeclaredField(name);
            if (!Modifier.isTransient(field.getModifiers())) {
                isAccessable = field.canAccess(source);
                field.setAccessible(true);
                return field.get(source);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessable)
                field.setAccessible(false);
        }
        return null;
    }
}
