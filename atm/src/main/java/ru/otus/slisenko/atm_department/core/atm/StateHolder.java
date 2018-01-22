package ru.otus.slisenko.atm_department.core.atm;

import java.util.HashMap;
import java.util.Map;

public class StateHolder {
    private Map<Denomination, Integer> state;

    public StateHolder(Map<Denomination, Integer> state) {
        this.state = new HashMap<>(state);
    }

    public Map<Denomination, Integer> getState() {
        return state;
    }
}
