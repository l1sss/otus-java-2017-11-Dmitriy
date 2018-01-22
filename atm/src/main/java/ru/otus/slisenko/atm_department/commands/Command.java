package ru.otus.slisenko.atm_department.commands;

import ru.otus.slisenko.atm_department.core.atm.Atm;

public interface Command {
    CommandResult execute(Atm atm);
}
