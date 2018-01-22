package ru.otus.slisenko.atm_department.core.atm;

import ru.otus.slisenko.atm_department.commands.Command;
import ru.otus.slisenko.atm_department.commands.CommandResult;
import ru.otus.slisenko.atm_department.core.AtmObserver;
import ru.otus.slisenko.atm_department.exceptions.ATMException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AtmImp implements Atm, AtmObserver {
    private Map<Denomination, Integer> dispensers;
    private StateHolder defaultState;

    public AtmImp() {
        dispensers = new HashMap<>();
        defaultState = new StateHolder(new HashMap<>());
    }

    @Override
    public void deposit(Denomination denomination, int notes) {
        int totalNotes = dispensers.getOrDefault(denomination, 0) + notes;
        dispensers.put(denomination, totalNotes);
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
                remain -= denomination.getValue() * notesToDispense;
            }
        }

        if (remain > 0)
            throw new ATMException(String.format("Can not dispense amount %d by dispenser %s", amount, dispensers));

        withdrawal.forEach((denomination, notesDispensed) ->
                dispensers.compute(denomination, (key, notes) -> notes - notesDispensed));

        return withdrawal;
    }

    @Override
    public void restoreDefaultState() {
        dispensers = new HashMap<>(defaultState.getState());
    }

    public void setNewDefaultState(Map<Denomination, Integer> newDefaultState) {
        defaultState = new StateHolder(newDefaultState);
    }

    @Override
    public int getBalance() {
        return dispensers.entrySet()
                .stream()
                .mapToInt((entry) -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    @Override
    public CommandResult notify(Command command) {
        return command.execute(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtmImp atmImp = (AtmImp) o;
        return Objects.equals(dispensers, atmImp.dispensers) &&
                Objects.equals(defaultState, atmImp.defaultState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dispensers, defaultState);
    }
}