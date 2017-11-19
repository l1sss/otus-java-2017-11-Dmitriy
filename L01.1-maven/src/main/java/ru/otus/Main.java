package ru.otus;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

public class Main {
    public static void main(String[] args) {
        Multiset<String> hello = LinkedHashMultiset.create();
        hello.add("Hello!");
        hello.add("Hello!");
        hello.add("Otus!");

        hello.forEach(System.out::println);
    }
}
