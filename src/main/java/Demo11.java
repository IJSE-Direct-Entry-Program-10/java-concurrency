public class Demo11 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
