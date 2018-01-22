package ru.otus.slisenko.atm.core;

import java.util.Map;

public interface Atm {
    int getBalance();

    void deposit(Denomination denomination, int notes);

    Map<Denomination, Integer> withdraw(final int amount);

    void restoreDefaultState();
}
