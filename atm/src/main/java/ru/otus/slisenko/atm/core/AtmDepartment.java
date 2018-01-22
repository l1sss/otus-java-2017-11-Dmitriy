package ru.otus.slisenko.atm.core;

public interface AtmDepartment {
    void register(AtmObserver atm);

    int getTotalBalance();

    void restoreToDefaultAllATM();
}
