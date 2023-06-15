package bootstrap;

public class Demo8 {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
        }
        myThread.join();
        System.out.println("Process is about to die");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
            }
        }
    }
}
