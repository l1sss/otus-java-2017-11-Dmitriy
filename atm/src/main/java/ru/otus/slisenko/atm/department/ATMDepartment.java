package ru.otus.slisenko.atm.department;

import ru.otus.slisenko.atm.core.ATM;

public interface ATMDepartment {
    void addATM(ATM atm);

    void removeATM(String id);

    int getParkSize();

    int getTotalBalance();

    void restoreToDefaultAllATM();
}
