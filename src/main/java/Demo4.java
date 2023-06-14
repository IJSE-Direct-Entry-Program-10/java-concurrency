import java.lang.reflect.Executable;
import java.util.concurrent.Executor;

public class Demo4 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new RunnableImpl(), "t1");
        t1.start();
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
        }
        System.out.println("Main thread is about to die");
        System.out.println("main: But hey, let's wait for the t1");
        //while (t1.isAlive());
        t1.join();
        System.out.println("main: Okay, let's rest in peace");
    }

    static class RunnableImpl implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
            }
            System.out.println("t1 thread is also about to die");
        }
    }
}
