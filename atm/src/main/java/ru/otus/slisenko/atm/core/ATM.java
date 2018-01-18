package ru.otus.slisenko.atm.core;

import java.util.Map;

public interface ATM {
    String getId();

    int getBalance();

    void deposit(Denomination denomination, int notes);

    Map<Denomination, Integer> withdraw(final int amount);

    void restoreToDefaultState();

    void setNewDefaultState(Map<Denomination, Integer> newDefaultState);
}
