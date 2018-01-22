package ru.otus.slisenko.atm_department.core;

import ru.otus.slisenko.atm_department.commands.Command;
import ru.otus.slisenko.atm_department.commands.CommandResult;

public interface AtmObserver {
    CommandResult notify(Command command);
}
