package ru.otus.slisenko.atm_department;

import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.atm_department.core.department.AtmDepartment;
import ru.otus.slisenko.atm_department.core.department.AtmDepartmentImp;
import ru.otus.slisenko.atm_department.core.atm.AtmImp;
import ru.otus.slisenko.atm_department.core.atm.Denomination;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestAtmDepartment {
    private AtmDepartment department;

    @Before
    public void init() {
        department = new AtmDepartmentImp();
    }

    @Test
    public void checkTotalBalance() {
        AtmImp atm1 = new AtmImp();
        atm1.deposit(Denomination.FIVE_THOUSAND, 1);
        department.register(atm1);

        AtmImp atm2 = new AtmImp();
        atm2.deposit(Denomination.ONE_HUNDRED, 9);
        department.register(atm2);

        assertEquals(5900, department.getTotalBalance());
    }

    @Test
    public void resetToDefaultStateAll() {
        AtmImp atm1 = new AtmImp();
        department.register(atm1);

        AtmImp atm2 = new AtmImp();
        Map<Denomination, Integer> newDefaultState = new HashMap<>();
        newDefaultState.put(Denomination.FIVE_THOUSAND, 5);
        atm2.setNewDefaultState(newDefaultState);
        department.register(atm2);

        department.restoreToDefaultAllATM();
        assertEquals(25_000, department.getTotalBalance());
    }
}
