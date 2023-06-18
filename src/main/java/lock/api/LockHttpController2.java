package lock.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/v2/locks")
public class LockHttpController2 {

    private final static ReentrantLock lock1 = new ReentrantLock();
    private final static ReentrantLock lock2 = new ReentrantLock();

    public LockHttpController2() {
    }

    static void log(String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }

    @GetMapping("/1")
    public String acquireLock1() throws InterruptedException {
        while (true) {
            log("Request for lock1");
            if (!lock1.tryLock()) {
                log("Failed to obtain the lock1, trying again!");
                continue;
            }
            log("Obtained lock1");
            SharedObject1.myMethod();
            log("Request for lock2");
            if (!lock2.tryLock()) {
                log("Failed to obtain the lock2, so releasing lock1 and trying again!");
                lock1.unlock();
            } else {
                log("Obtained lock2");
                SharedObject2.myMethod();
                lock1.unlock();
                lock2.unlock();
                return "<h1>Hello There! I am Lock1</h1>";
            }
        }
    }

    @GetMapping("/2")
    public String acquireLock2() throws InterruptedException {
        while (true) {
            log("Request for lock2");
            if (!lock2.tryLock()) {
                log("Failed to obtain the lock2, trying again!");
                continue;
            }
            log("Obtained lock2");
            SharedObject2.myMethod();
            log("Request for lock1");
            if (!lock1.tryLock()) {
                log("Failed to obtain the lock1, so releasing lock 2 trying again!");
                lock2.unlock();
            } else {
                log("Obtained lock1");
                SharedObject1.myMethod();
                lock1.unlock();
                lock2.unlock();
                return "<h1>Hello There! I am Lock2</h1>";
            }
        }
    }

    static class SharedObject1 {

        static void myMethod() throws InterruptedException {
            log("Entered into the so1:method");
            Thread.sleep(1000);
            log("About to exit from so1:method");
        }

    }

    static class SharedObject2 {

        static void myMethod() throws InterruptedException {
            log("Entered into the so2:method");
            Thread.sleep(1000);
            log("About to exit from so2:method");
        }

    }

}

