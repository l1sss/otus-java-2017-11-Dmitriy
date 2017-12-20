package ru.otus;

import ru.otus.testclass.TestClass;
import ru.otus.testrunner.TestRunner;

public class Main {
    public static void main(String[] args) {
        //TestRunner.runTestsFromClass(TestClass.class);

        String pack = "ru.otus.testclass";
        TestRunner.loadClassesFromPackage(pack);
    }
}
