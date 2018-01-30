package ru.otus.slisenko.json_object_writer.classes_for_test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ObjectWithIterable {
    private List<String> list = Arrays.asList("some", "string", "values");
    private Set<Double> set = Set.of(1.99, 14.00, 29.4);
}
