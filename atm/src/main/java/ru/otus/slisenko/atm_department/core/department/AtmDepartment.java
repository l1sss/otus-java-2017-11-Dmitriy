package ru.otus.slisenko.atm_department.core.department;

import ru.otus.slisenko.atm_department.core.AtmObserver;

public interface AtmDepartment {
    void register(AtmObserver atm);

    int getTotalBalance();

    void restoreToDefaultAllATM();
}
