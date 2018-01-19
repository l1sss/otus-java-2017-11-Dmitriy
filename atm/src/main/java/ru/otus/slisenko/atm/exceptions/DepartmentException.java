package ru.otus.slisenko.atm.exceptions;

public class DepartmentException extends RuntimeException {

    public DepartmentException() {
        super();
    }

    public DepartmentException(String message) {
        super(message);
    }
}
