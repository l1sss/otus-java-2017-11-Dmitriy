package ru.otus.testrunner;

public class TestError extends Error {
    public TestError(String message) {
        super(message);
    }
}
