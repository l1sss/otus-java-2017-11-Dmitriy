package ru.otus;

import java.util.Stack;

public class MemoryEater {
    private static final int objsInSecond = 36_000; //if 1/2 objects delete -> we will receive OOM in ~5 minutes

    public static void eatMemory() throws InterruptedException {
        Stack<String> stack = new Stack<>();

        System.out.println("Start eat memory!");
        long start = System.currentTimeMillis();
        long finish = 0;

        while (true) {
            for (int i = 0; i < objsInSecond; i++) {
                stack.add(new String(new char[0]));

                if (i % 2 == 0)
                    stack.pop();
            }

            Thread.sleep(1000);

            finish = (System.currentTimeMillis() - start) / 1000;
            if (finish % 10 == 0)
                System.out.println(finish + " sec spent");
        }
    }
}
