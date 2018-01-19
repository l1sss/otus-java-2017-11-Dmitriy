package ru.otus.slisenko.atm.exceptions;

public class ATMException extends RuntimeException {

    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }
}
