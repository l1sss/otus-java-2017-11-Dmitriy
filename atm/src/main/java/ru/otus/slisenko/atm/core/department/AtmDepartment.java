package ru.otus.slisenko.atm.core.department;

import ru.otus.slisenko.atm.core.AtmObserver;

public interface AtmDepartment {
    void register(AtmObserver atm);

    int getTotalBalance();

    void restoreToDefaultAllATM();
}
