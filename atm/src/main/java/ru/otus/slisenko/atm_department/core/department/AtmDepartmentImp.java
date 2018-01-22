package ru.otus.slisenko.atm_department.core.department;

import ru.otus.slisenko.atm_department.commands.GetBalanceCommand;
import ru.otus.slisenko.atm_department.commands.RestoreDefaultCommand;
import ru.otus.slisenko.atm_department.core.AtmObserver;

import java.util.HashSet;
import java.util.Set;

public class AtmDepartmentImp implements AtmDepartment {
    private Set<AtmObserver> atmSet;

    public AtmDepartmentImp() {
        atmSet = new HashSet<>();
    }

    @Override
    public void register(AtmObserver atm) {
        atmSet.add(atm);
    }

    @Override
    public int getTotalBalance() {
        return atmSet.stream()
                .mapToInt((atm) -> atm.notify(new GetBalanceCommand()).getResult())
                .sum();
    }

    @Override
    public void restoreToDefaultAllATM() {
        atmSet.forEach((atm) -> atm.notify(new RestoreDefaultCommand()));
    }
}
