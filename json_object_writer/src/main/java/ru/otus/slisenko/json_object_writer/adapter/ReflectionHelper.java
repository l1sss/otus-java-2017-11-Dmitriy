package ru.otus.slisenko.json_object_writer.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class ReflectionHelper {
    private ReflectionHelper() {
    }

    static Object getFieldValue(Object source, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = source.getClass().getDeclaredField(name);
            if (!Modifier.isTransient(field.getModifiers())) {
                isAccessible = field.canAccess(source);
                field.setAccessible(true);
                return field.get(source);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible)
                field.setAccessible(false);
        }
        return null;
    }
}
