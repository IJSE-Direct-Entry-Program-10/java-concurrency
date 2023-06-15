package lock.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/v2/locks")
public class LockHttpController2 {

    private final static ReentrantLock lock1 = new ReentrantLock();
    private final static ReentrantLock lock2 = new ReentrantLock();

    @GetMapping("/1")
    public void acquireLock1(){
        SharedObject1.method1();
    }

    @GetMapping("/2")
    public void acquireLock2(){
        SharedObject2.method1();
    }

    public static boolean flag = false;

    static class SharedObject1{
        static void method1(){
            lock1.lock();
            System.out.println(Thread.currentThread().getName() + " entered into so1:method1");
            System.out.println(Thread.currentThread().getName() + " is executing some code");
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " is about to enter into so2:method2");
                while (true){
                    if (flag){
                        lock1.unlock();
                        flag = false;
                    }
                    if (!lock2.tryLock()){
                        lock1.lock();
                    }else{
                        SharedObject2.method2();
                        lock2.unlock();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        static void method2(){
            System.out.println(Thread.currentThread().getName() + " entered into so1:method2");
        }
    }

    static class SharedObject2{
        static void method1(){
            lock2.lock();
            System.out.println(Thread.currentThread().getName() + " entered into so2:method1");
            System.out.println(Thread.currentThread().getName() + " is executing some code");
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " is about to enter into so1:method2");
                while (true){
                    if (!flag){
                        lock2.unlock();
                        flag = true;
                    }
                    if (!lock1.tryLock()){
                        lock2.lock();
                    }else{
                        SharedObject1.method2();
                        lock1.unlock();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        static void method2(){
            System.out.println(Thread.currentThread().getName() + " entered into so2:method2");
        }
    }
}

