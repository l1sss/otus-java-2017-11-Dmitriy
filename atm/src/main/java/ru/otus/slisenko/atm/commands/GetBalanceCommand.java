package ru.otus.slisenko.atm.commands;

import ru.otus.slisenko.atm.core.atm.Atm;

public class GetBalanceCommand implements Command {

    @Override
    public CommandResult execute(Atm atm) {
        return new CommandResult(atm.getBalance());
    }
}
