package ru.otus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new CustomArrayList<>();

        list1.add(3);
        System.out.println("after list1.add(3) = " + list1 + "\n");

        Collections.addAll(list1, 2, 1, 0);
        System.out.println("list1 after Collections.addAll(list1, 2, 1, 0) = " + list1 + "\n");

        List<Integer> list2 = new CustomArrayList<>(list1.size());
        Collections.addAll(list2, 4, 5, 6, 7);
        System.out.println("after Collections.addAll(list2, 4, 5, 6, 7) = " + list2+ "\n");

        Collections.copy(list2, list1);
        System.out.println("list2 after Collections.copy(list2, list) = " + list2+ "\n");

        Collections.sort(list2, Comparator.comparingInt(o -> o));
        System.out.println("list2 after Collections.sort = " + list2);
    }
}
