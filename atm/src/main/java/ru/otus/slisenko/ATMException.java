package ru.otus.slisenko;

public class ATMException extends RuntimeException {

    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }
}
