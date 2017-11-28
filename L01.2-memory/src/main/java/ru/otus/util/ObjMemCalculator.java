package ru.otus.util;

public class ObjMemCalculator {
    private static final int ARRAY_SIZE = 2_000_000;
    private static final int SLEEP_TIME = 100;
    private ObjectCreator objectCreator;

    public ObjMemCalculator(ObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
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
            obj[i] = objectCreator.create();

        after = rt.totalMemory() - rt.freeMemory();

        return (after - before) / ARRAY_SIZE;
    }

    public void setObjectCreator(ObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }
}
