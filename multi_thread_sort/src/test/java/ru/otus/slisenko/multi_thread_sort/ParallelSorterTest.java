package ru.otus.slisenko.multi_thread_sort;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ParallelSorterTest {
    private static final Random generator = new Random();
    private static final int ARRAY_SIZE = 5000;
    private int[] array;

    @Before
    public void setup() {
        this.array = new int[ARRAY_SIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = generator.nextInt(1000);
        }
    }

    @Test
    public void shouldSortArray() {
        ParallelSorter.mergeSort(array);

        boolean isSorted = isSorted(array);

        assertEquals(true, isSorted);
    }

    @Test
    public void checkSum() {
        int sumBeforeSort = getSum(array);

        ParallelSorter.mergeSort(array);

        int sumAfterSort = getSum(array);
        assertEquals(sumBeforeSort, sumAfterSort);
    }

    private boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }

    private int getSum(int[] array) {
        return Arrays.stream(array).sum();
    }
}
