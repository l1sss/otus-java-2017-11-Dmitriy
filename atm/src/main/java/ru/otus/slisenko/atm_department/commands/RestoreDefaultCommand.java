package ru.otus.slisenko.atm_department.commands;

import ru.otus.slisenko.atm_department.core.atm.Atm;

public class RestoreDefaultCommand implements Command {

    @Override
    public CommandResult execute(Atm atm) {
        atm.restoreDefaultState();

        return new CommandResult(1);
    }
}
