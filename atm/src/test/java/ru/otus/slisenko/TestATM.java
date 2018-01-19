package ru.otus.slisenko;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestATM {
    private ATM atm;

    @Before
    public void init() {
        atm = new ATM();
    }

    @Test
    public void getBalanceOfEmptyATM() {
        assertEquals(0, atm.getBalance());
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
}
