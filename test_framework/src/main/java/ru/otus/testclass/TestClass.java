package ru.otus.testclass;

import ru.otus.testrunner.TestError;
import ru.otus.testrunner.annotations.After;
import ru.otus.testrunner.annotations.Before;
import ru.otus.testrunner.annotations.Test;

public class TestClass {
    public TestClass() {
    }

    @Before
    public void doBeforeMethod() {
        System.out.println("TestClass.doBeforeMethod");
    }

    public void doTest0Method() {
        System.out.println("TestClass.doTest0Method");
    }

    @Test
    public void doTest1Method() throws TestError {
        System.out.println("TestClass.doTest1Method");
        throw new TestError();
    }

    @Test
    public void doTest2Method() {
        System.out.println("TestClass.doTest2Method");
    }

    @After
    public void doAfterMethod() {
        System.out.println("TestClass.doAfterMethod");
    }
}
