package ru.otus.testclass;

import ru.otus.testrunner.Assert;
import ru.otus.testrunner.TestError;
import ru.otus.testrunner.annotations.After;
import ru.otus.testrunner.annotations.Before;
import ru.otus.testrunner.annotations.Test;

public class TestClass {
    public TestClass() {
    }

    @Before
    public void doBeforeMethod() {
        System.out.println("doBeforeMethod");
    }

    //without @Test
    public void doTest0Method() {
        throw new TestError("method is called without @Test annotation");
    }

    @Test
    public void doTest1Method() {
        Assert.assertEquals("1", 1);
    }

    @Test
    public void doTest2Method() {
        Assert.assertEquals(2, new Integer(2));
    }

    @After
    public void doAfterMethod() {
        System.out.println("doAfterMethod");
    }
}
