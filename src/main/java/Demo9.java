public class Demo9 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
                }
            }
        };
        t1.start();
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%s:%d %n", Thread.currentThread().getName(), i);
        }
        t1.join();
        System.out.println("Process is about to die");
    }
}
