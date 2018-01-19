package ru.otus.slisenko.atm;

import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.atm.core.ATM;
import ru.otus.slisenko.atm.core.Denomination;
import ru.otus.slisenko.atm.core.ATMImp;
import ru.otus.slisenko.atm.department.ATMDepartment;
import ru.otus.slisenko.atm.department.ATMDepartmentImp;
import ru.otus.slisenko.atm.exceptions.ATMException;
import ru.otus.slisenko.atm.exceptions.DepartmentException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestATMDepartment {
    private ATMDepartment department;

    @Before
    public void init() {
        department = new ATMDepartmentImp();
    }

    @Test
    public void addSeveralATM() {
        ATM atm1 = new ATMImp();
        department.addATM(atm1);
        assertEquals(1, department.getParkSize());

        ATM atm2 = new ATMImp();
        department.addATM(atm2);
        assertEquals(2, department.getParkSize());
    }

    @Test
    public void removeATM() {
        ATM atm1 = new ATMImp();
        department.addATM(atm1);

        ATM atm2 = new ATMImp();
        department.addATM(atm2);

        department.removeATM(atm1.getId());

        assertEquals(1, department.getParkSize());
    }

    @Test(expected = DepartmentException.class)
    public void removeUnregisteredATM() {
        ATM atm1 = new ATMImp();
        department.addATM(atm1);

        ATM atm2 = new ATMImp();
        department.addATM(atm2);

        department.removeATM("1515");
    }

    @Test
    public void checkTotalBalance() {
        ATM atm1 = new ATMImp();
        atm1.deposit(Denomination.FIVE_THOUSAND, 1);
        department.addATM(atm1);

        ATM atm2 = new ATMImp();
        atm2.deposit(Denomination.ONE_HUNDRED, 9);
        department.addATM(atm2);

        assertEquals(5900, department.getTotalBalance());
    }

    @Test
    public void resetToDefaultStateAll() {
        ATM atm1 = new ATMImp();
        department.addATM(atm1);

        ATM atm2 = new ATMImp();
        Map<Denomination, Integer> newDefaultState = new HashMap<>();
        newDefaultState.put(Denomination.FIVE_THOUSAND, 5);
        atm2.setNewDefaultState(newDefaultState);
        department.addATM(atm2);

        department.restoreToDefaultAllATM();
        assertEquals(25_000, department.getTotalBalance());
    }
}
