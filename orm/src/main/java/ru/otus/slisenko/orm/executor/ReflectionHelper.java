package ru.otus.slisenko.orm.executor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

class ReflectionHelper {

    private ReflectionHelper() {
    }

    static <T> T instantiate(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Object getFieldValue(Object source, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = source.getClass().getDeclaredField(name);
            isAccessible = field.canAccess(source);
            field.setAccessible(true);
            return field.get(source);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    static void setFieldValue(Object source, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = source.getClass().getDeclaredField(name);
            isAccessible = field.canAccess(source);
            field.setAccessible(true);
            field.set(source, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }
}
