package ru.otus.slisenko.atm.commands;

import ru.otus.slisenko.atm.core.Atm;

public interface Command {
    CommandResult execute(Atm atm);
}
