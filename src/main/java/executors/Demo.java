package executors;

import java.util.concurrent.*;

public class Demo {

    public static void main(String[] args) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->System.out.println("IJSE"));

        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        executorService.submit(Demo::print);
//        executorService.submit(Demo::print);
//        executorService.submit(Demo::print);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.schedule(Demo::print, 5, TimeUnit.SECONDS);
//        System.out.println("I am working here");

        scheduledExecutorService.scheduleWithFixedDelay(()-> System.out.println("IJSE"),
                0, 5, TimeUnit.SECONDS);

        System.out.println("Main thread is free");
    }

    public static void print(){
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
        }
        System.out.println("----------------------");
    }
}
