package bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Demo12 {
    static List<String> elements = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                add("t1", i);
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                add("t2", i);
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        elements.forEach(System.out::println);
    }

    public static void add(String threadName, int iteration) {
        elements.add(String.format("%s:%d", threadName, iteration));
        try {
            Thread.sleep(threadName.equals("t1") ? 1 : 2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
