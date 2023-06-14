public class Demo3 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new RunnableImpl(), "t1");
        t1.start();
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
        }
        System.out.println("Main thread is about to die");
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
