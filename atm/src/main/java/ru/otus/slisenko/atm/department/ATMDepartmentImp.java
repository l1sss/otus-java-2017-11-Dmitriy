package ru.otus.slisenko.atm.department;

import ru.otus.slisenko.atm.core.ATM;
import ru.otus.slisenko.atm.exceptions.ATMException;
import ru.otus.slisenko.atm.exceptions.DepartmentException;

import java.util.HashMap;
import java.util.Map;

public class ATMDepartmentImp implements ATMDepartment {
    private Map<String, ATM> park = new HashMap<>();

    @Override
    public void addATM(ATM atm) {
        park.put(atm.getId(), atm);
    }

    @Override
    public void removeATM(String id) {
        if (park.remove(id) == null)
            throw new DepartmentException(String.format("ATM with serial number \"%s\" is not registered in department", id));
    }

    @Override
    public int getParkSize() {
        return park.size();
    }

    @Override
    public int getTotalBalance() {
        return park.entrySet()
                .stream()
                .mapToInt((entry) -> entry.getValue().getBalance())
                .sum();
    }

    @Override
    public void restoreToDefaultAllATM() {
        park.forEach((key, value) -> value.restoreToDefaultState());
    }
}
