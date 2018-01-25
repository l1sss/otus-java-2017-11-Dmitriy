package ru.otus.slisenko.atm_department.commands;

public class CommandResult<T> {
    private T result;

    public CommandResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
