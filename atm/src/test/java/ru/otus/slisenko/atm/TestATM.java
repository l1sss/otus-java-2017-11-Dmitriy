package ru.otus.slisenko.atm;

import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.atm.core.ATM;
import ru.otus.slisenko.atm.core.Denomination;
import ru.otus.slisenko.atm.core.ATMImp;
import ru.otus.slisenko.atm.exceptions.ATMException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestATM {
    private ATM atm;

    @Before
    public void init() {
        atm = new ATMImp();
    }

    @Test
    public void getBalanceOfEmptyATM() {
        assertEquals(0, atm.getBalance());
    }

    @Test
    public void getId() {
        assertNotNull(atm.getId());
    }

    @Test
    public void putNotesOfDifferentDenominations() {
        atm.deposit(Denomination.ONE_HUNDRED, 3);
        atm.deposit(Denomination.FIVE_HUNDRED, 2);
        atm.deposit(Denomination.ONE_THOUSAND, 1);
        atm.deposit(Denomination.FIVE_THOUSAND, 2);
        atm.deposit(Denomination.ONE_HUNDRED, 1);

        assertEquals(12400, atm.getBalance());
    }

    @Test
    public void withdrawMinimizedCountOfNotes() {
        atm.deposit(Denomination.FIVE_THOUSAND, 1);
        atm.deposit(Denomination.ONE_THOUSAND, 5);
        atm.deposit(Denomination.FIVE_HUNDRED, 1);
        atm.deposit(Denomination.ONE_HUNDRED, 5);
        Map<Denomination, Integer> withdrawal = atm.withdraw(5500);

        assertEquals(5500, atm.getBalance());
        assertEquals(1, withdrawal.get(Denomination.FIVE_THOUSAND).intValue());
        assertEquals(1, withdrawal.get(Denomination.FIVE_HUNDRED).intValue());
    }

    @Test(expected = ATMException.class)
    public void throwExceptionWhenAmountCanNotWithdraw() {
        atm.deposit(Denomination.FIVE_THOUSAND, 1);
        atm.withdraw(300);
    }

    @Test
    public void checkBalanceAfterFailWithdraw() {
        atm.deposit(Denomination.FIVE_THOUSAND, 1);
        atm.deposit(Denomination.FIVE_HUNDRED, 1);
        atm.deposit(Denomination.ONE_HUNDRED, 4);
        assertEquals(5900, atm.getBalance());
        try {
            atm.withdraw(1000);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(5900, atm.getBalance());
    }

    @Test
    public void restoreToDefaultState() {
        atm.deposit(Denomination.FIVE_HUNDRED, 1);
        atm.restoreToDefaultState();
        assertEquals(0, atm.getBalance());

        atm.deposit(Denomination.FIVE_THOUSAND, 1);
        atm.restoreToDefaultState();
        assertEquals(0, atm.getBalance());
    }

    @Test
    public void setNewDefaultState() {
        atm.deposit(Denomination.FIVE_THOUSAND, 2);
        Map<Denomination, Integer> newDefaultState = new HashMap<>();
        newDefaultState.put(Denomination.ONE_HUNDRED, 1);
        atm.setNewDefaultState(newDefaultState);
        atm.restoreToDefaultState();
        assertEquals(100, atm.getBalance());

        newDefaultState.put(Denomination.ONE_THOUSAND, 1);
        atm.restoreToDefaultState();
        assertEquals(100, atm.getBalance());
    }
}
