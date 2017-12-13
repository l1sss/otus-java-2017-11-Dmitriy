package ru.otus.util;

import java.util.Stack;

public class MemoryEater {
    private static final int objsInSecond = 36_000; //if 1/2 objects delete -> we will receive OOM in ~5 minutes

    public static void eatMemory() throws InterruptedException {
        Stack<String> stack = new Stack<>();

        System.out.println("Start eat memory!");

        while (true) {
            for (int i = 0; i < objsInSecond; i++) {
                stack.add(new String(new char[0]));

                if (i % 2 == 0)
                    stack.pop();
            }

            Thread.sleep(1000);
        }
    }
}
