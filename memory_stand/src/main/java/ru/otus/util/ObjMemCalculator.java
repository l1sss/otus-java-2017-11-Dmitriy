package ru.otus.util;

import java.util.function.Supplier;

public class ObjMemCalculator {
    private static final int ARRAY_SIZE = 5_000_000;
    private static final int SLEEP_TIME = 100;
    private Supplier objectCreator;

    public ObjMemCalculator(Supplier supplier) {
        this.objectCreator = supplier;
    }

    public long getObjectSize() throws InterruptedException {
        Object[] obj = new Object[ARRAY_SIZE];
        Runtime rt = Runtime.getRuntime();
        long before;
        long after;

        System.gc();
        Thread.sleep(SLEEP_TIME);

        before = rt.totalMemory() - rt.freeMemory();

        for (int i = 0; i < ARRAY_SIZE; i++)
            obj[i] = objectCreator.get();

        after = rt.totalMemory() - rt.freeMemory();

        return (after - before) / ARRAY_SIZE;
    }

    public void setObjectCreator(Supplier supplier) {
        this.objectCreator = supplier;
    }
}
