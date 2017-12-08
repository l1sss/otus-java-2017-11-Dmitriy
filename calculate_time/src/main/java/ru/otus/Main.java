package ru.otus;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    private static final int MEASURE_COUNT = 1000;
    private static final int WARMUP_COUNT = 1000;

    public static void main(String[] args) {
        Collection<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 1_000_000;
        for (int i = min; i < max; i++) {
            example.add(i);
        }

        calcTime(() -> example.contains(9_999_999));
    }

    private static void calcTime(Runnable runnable) {
        for (int i = 0; i < WARMUP_COUNT; i++)
            runnable.run();

        long startTime = System.nanoTime();

        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();

        long finishTime = System.nanoTime();
        long result = (finishTime - startTime) / MEASURE_COUNT;

        System.out.println("Time spent: " + result + "ns (" + result / 1_000_000 + ")");
    }
}
