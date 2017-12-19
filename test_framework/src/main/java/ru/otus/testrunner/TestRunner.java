package ru.otus.testrunner;

import ru.otus.testrunner.annotations.After;
import ru.otus.testrunner.annotations.Before;
import ru.otus.testrunner.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestRunner {
    private TestRunner() {
    }

    public static <T> void runClassTest(Class<T> clazz) {
        T instance = ReflectionHelper.instantiate(clazz);
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
            for (Method b : before)
                ReflectionHelper.callMethod(instance, b.getName());

            callTest(instance, t.getName());

            for (Method a : after)
                ReflectionHelper.callMethod(instance, a.getName());
        }
    }

    public static void loadPackageClasses(String packageName) throws IOException {
        Path source = Paths.get(packageName);
        System.out.println("LOAD : " + source);

        List<Path> classPaths = Files.list(source)
                .filter(path -> path.toString().endsWith(".class"))
                .collect(Collectors.toList());

        MyClassLoader cl = new MyClassLoader();
        try {
            for (Path p : classPaths) {
                Class clazz = cl.findClass(p.toString());
                runClassTest(clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void callTest(Object object, String name) {
        try {
            ReflectionHelper.callMethod(object, name);
            System.out.println(name + " - DONE");
        } catch (TestError e) {
            System.out.println(name + " - FAILED");
        }
    }
}
