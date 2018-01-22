package ru.otus.slisenko.atm_department.exceptions;

public class ATMException extends RuntimeException {

    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }
}
