package ru.otus.slisenko.atm.core;

import java.util.HashMap;
import java.util.Map;

public class DefaultState {
    private Map<Denomination, Integer> state;

    public DefaultState(Map<Denomination, Integer> state) {
        this.state = new HashMap<>(state);
    }

    public Map<Denomination, Integer> getState() {
        return state;
    }
}
