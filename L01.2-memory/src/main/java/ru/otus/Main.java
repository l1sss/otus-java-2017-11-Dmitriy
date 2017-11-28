package ru.otus;

import ru.otus.util.ObjMemCalculator;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String f = "%-35s%17d%n";
        System.out.println("<<<<<<<<<<<<<<<<<<<<MEMORY_STAND>>>>>>>>>>>>>>>>>>>>");
        System.out.printf("%-35s%17s%n%n", "OBJECT", "BYTES");

        ObjMemCalculator calculator = new ObjMemCalculator(() -> new String(new char[0]));
        System.out.printf(f, "Empty String: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new String("a"));
        System.out.printf(f, "String from pool: ", calculator.getObjectSize());

        calculator.setObjectCreator(Object::new);
        System.out.printf(f, "Object: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new Object[0]);
        System.out.printf(f, "Empty Object array: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new Object[1]);
        System.out.printf(f, "Object array size 1: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new Object[2]);
        System.out.printf(f, "Object array size 2: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new Object[3]);
        System.out.printf(f, "Object array size 3: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new Object[10]);
        System.out.printf(f, "Object array size 10: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new Integer(1));
        System.out.printf(f, "Integer: ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new MyClass<>(new Object()));
        System.out.printf(f, "MyClass<T1> : ", calculator.getObjectSize());

        calculator.setObjectCreator(() -> new MyClass2<>(new Object(), new Integer(1)));
        System.out.printf(f, "MyClass2<T1, T2> : ", calculator.getObjectSize());
    }
}
