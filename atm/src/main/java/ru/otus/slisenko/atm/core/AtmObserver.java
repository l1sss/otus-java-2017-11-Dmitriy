package ru.otus.slisenko.atm.core;

import ru.otus.slisenko.atm.commands.Command;
import ru.otus.slisenko.atm.commands.CommandResult;

public interface AtmObserver {
    CommandResult notify(Command command);
}
