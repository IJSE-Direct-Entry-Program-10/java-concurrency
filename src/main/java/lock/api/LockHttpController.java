package lock.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locks")
public class LockHttpController {

    @GetMapping("/1")
    public void acquireLock1(){
        SharedObject1.method1();
    }

    @GetMapping("/2")
    public void acquireLock2(){
        SharedObject2.method1();
    }

    static class SharedObject1{
        static synchronized void method1(){
            System.out.println(Thread.currentThread().getName() + " entered into so1:method1");
            System.out.println(Thread.currentThread().getName() + " is executing some code");
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " is about to enter into so2:method2");
                SharedObject2.method2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        static synchronized void method2(){
            System.out.println(Thread.currentThread().getName() + " entered into so1:method2");
        }
    }

    static class SharedObject2{
        static synchronized void method1(){
            System.out.println(Thread.currentThread().getName() + " entered into so2:method1");
            System.out.println(Thread.currentThread().getName() + " is executing some code");
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " is about to enter into so1:method2");
                SharedObject1.method2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        static synchronized void method2(){
            System.out.println(Thread.currentThread().getName() + " entered into so2:method2");
        }
    }
}

