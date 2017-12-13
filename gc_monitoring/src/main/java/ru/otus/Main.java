package ru.otus;

/*
-Xmx256m
-Xms256m
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xloggc:gc.log
-XX:GCLogFileSize=10M
======================
-XX:+UseSerialGC            Total GC count: 10, total duration: 3142 ms
-XX:+UseParallelGC          Total GC count: 11, total duration: 9534 ms
-XX:+UseConcMarkSweepGC     Total GC count: 73, total duration: 70077 ms
-XX:+UseG1GC                Total GC count: 26, total duration: 8692 ms
*/

import ru.otus.util.MemoryEater;
import ru.otus.util.MemoryUtil;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try {
            MemoryUtil.startGCMonitor();
            MemoryEater.eatMemory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(500);
            MemoryUtil.printTotal();
        }
    }
}
