package deamon;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Main thread, are you there? " + mainThread.isAlive());
            System.out.println("t1 thread is also about to die");
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        Thread.sleep(200);
        System.out.println("Main thread is about to die");
    }
}
