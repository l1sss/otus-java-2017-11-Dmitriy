package ru.otus.slisenko.atm_department.commands;

import ru.otus.slisenko.atm_department.core.atm.Atm;

public class GetBalanceCommand implements Command {

    @Override
    public CommandResult execute(Atm atm) {
        return new CommandResult<>(atm.getBalance());
    }
}
