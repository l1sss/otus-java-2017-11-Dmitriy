package ru.otus.slisenko.atm.commands;

import ru.otus.slisenko.atm.core.Atm;

public class RestoreDefaultCommand implements Command {

    @Override
    public CommandResult execute(Atm atm) {
        atm.restoreDefaultState();

        return new CommandResult(1);
    }
}
