package ru.otus;

import ru.otus.testrunner.TestRunner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //TestRunner.runClassTest(TestClass.class);
        TestRunner.loadPackageClasses("/home/l1s/IdeaProjects/otus/homeworks/test_framework/src/main/java/ru/otus/testclass/compiled/");
    }
}
