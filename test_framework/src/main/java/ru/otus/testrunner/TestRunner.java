package ru.otus.testrunner;

import com.google.common.reflect.ClassPath;
import ru.otus.testrunner.annotations.After;
import ru.otus.testrunner.annotations.Before;
import ru.otus.testrunner.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private TestRunner() {
    }

    public static void runTestsFromClass(Class<?> clazz) {
        System.out.println("RUN : " + clazz.getName());

        List<Method> before = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> after = new ArrayList<>();

        for (Method m : clazz.getMethods()) {
            if (m.isAnnotationPresent(Before.class)) before.add(m);
            else if (m.isAnnotationPresent(Test.class)) tests.add(m);
            else if (m.isAnnotationPresent(After.class)) after.add(m);
        }

        for (Method t : tests) {
            Object instance = ReflectionHelper.instantiate(clazz);

            for (Method b : before)
                ReflectionHelper.callMethod(instance, b.getName());

            invokeTest(instance, t.getName());

            for (Method a : after)
                ReflectionHelper.callMethod(instance, a.getName());
        }
    }

    public static void loadClassesFromPackage(String packageName) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ClassPath classpath = null;
        try {
            classpath = ClassPath.from(classLoader);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(packageName))
            runTestsFromClass(classInfo.load());
    }

    private static void invokeTest(Object object, String name) {
        try {
            ReflectionHelper.callMethod(object, name);
            System.out.println(name + " - DONE");
        } catch (TestError e) {
            System.out.println(name + " - FAILED");
            System.out.println(e.getMessage());
        }
    }
}
