package ru.otus.slisenko.multi_thread_sort;

import java.util.Arrays;

public class ParallelSorter {

    private ParallelSorter() {}

    public static void mergeSort(int[] array) {
        int cores = Runtime.getRuntime().availableProcessors();
        mergeSort(array, cores);
    }

    public static void mergeSort(int[] array, int threadCount) {
        if (threadCount > 1) {
            int size = array.length;
            int middle = size / 2;
            int[] leftPart = Arrays.copyOfRange(array, 0, middle);
            int[] rightPart = Arrays.copyOfRange(array, middle, size);

            Thread leftThread = new Thread(() -> mergeSort(leftPart, threadCount / 2));
            Thread rightThread = new Thread(() -> mergeSort(rightPart, threadCount / 2));
            leftThread.start();
            rightThread.start();

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            merge(leftPart, rightPart, array);

        } else {
            Arrays.sort(array);
        }
    }

    private static void merge(int[] leftPart, int[] rightPart, int[] array) {
        int i1 = 0;
        int i2 = 0;
        for (int i = 0; i < array.length; i++) {
            if (i2 >= rightPart.length || (i1 < leftPart.length && leftPart[i1] < rightPart[i2])) {
                array[i] = leftPart[i1];
                i1++;
            } else {
                array[i] = rightPart[i2];
                i2++;
            }
        }
    }
}
