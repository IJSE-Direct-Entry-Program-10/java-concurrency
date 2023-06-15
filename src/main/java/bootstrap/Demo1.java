package bootstrap;

public class Demo1 {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        Thread t1 = new Thread(new RunnableImpl(), "t1");

        t1.start();
    }

    static class RunnableImpl implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            System.out.println("IJSE");
        }
    }
}
