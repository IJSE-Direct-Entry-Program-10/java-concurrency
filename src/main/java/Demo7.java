import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Demo7 {

    public static void main(String[] args)  {
       Executor executor = Executors.newSingleThreadExecutor();
       executor.execute(new RunnableImpl());
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
        }
        System.out.println(Thread.currentThread().getName() + " is about to die");
    }

    static class RunnableImpl implements Runnable {

        @Override
        public void run(){
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
            }
            System.out.println(Thread.currentThread().getName() + " is about to die");
        }
    }
}
