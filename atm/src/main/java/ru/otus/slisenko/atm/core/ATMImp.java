package ru.otus.slisenko.atm.core;

import ru.otus.slisenko.atm.exception.ATMException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ATMImp implements ATM {
    private final String id;
    private static long ATMId;
    private Map<Denomination, Integer> dispensers = new HashMap<>();
    private DefaultState defaultState;

    public ATMImp() {
        id = Long.toString(++ATMId);
        defaultState = new DefaultState(new HashMap<>());
    }

    @Override
    public void deposit(Denomination denomination, int notes) {
        dispensers.put(denomination, notes);
    }

    @Override
    public Map<Denomination, Integer> withdraw(final int amount) {
        Map<Denomination, Integer> withdrawal = new HashMap<>();
        int remain = amount;

        for (Denomination denomination : Denomination.values()) {
            int notes = dispensers.getOrDefault(denomination, 0);
            if (notes > 0) {
                int notesNeeded = remain / denomination.getValue();
                int notesToDispense = Math.min(notes, notesNeeded);
                withdrawal.put(denomination, notesToDispense);
                remain -= denomination.getValue() * notesNeeded;
            }
        }

        if (remain > 0)
            throw new ATMException(String.format("Can not dispense amount %d by dispenser %s", amount, dispensers));

        withdrawal.forEach((denomination, notesDispensed) ->
                dispensers.compute(denomination, (key, notes) -> notes - notesDispensed));

        return withdrawal;
    }

    @Override
    public void restoreToDefaultState() {
        dispensers = new HashMap<>(defaultState.getState());
    }

    @Override
    public void setNewDefaultState(Map<Denomination, Integer> newDefaultState) {
        defaultState = new DefaultState(newDefaultState);
    }

    @Override
    public int getBalance() {
        return dispensers.entrySet()
                .stream()
                .mapToInt((entry) -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATMImp atmImp = (ATMImp) o;
        return Objects.equals(id, atmImp.id) &&
                Objects.equals(dispensers, atmImp.dispensers) &&
                Objects.equals(defaultState, atmImp.defaultState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dispensers, defaultState);
    }
}