package ru.otus;

/*
-Xmx256m
-Xms256m
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xloggc:gc.log
-XX:GCLogFileSize=10M
======================
-XX:+UseSerialGC
-XX:+UseParallelGC
-XX:+UseConcMarkSweepGC
-XX:+UseG1GC
*/

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MemoryEater.eatMemory();
    }
}
