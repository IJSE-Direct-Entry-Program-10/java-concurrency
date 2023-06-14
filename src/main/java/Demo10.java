public class Demo10 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
            }
        }, "t1");
        Thread t2 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
            }
        }, "t2");
        /* 1 (Min) = 5 (Norm) = 10 (Max) */
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
    }
}
