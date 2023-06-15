package synchronization.api;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/v2/customers")
public class CustomerHTTPController2 {

    private final ArrayList<String> customerNameList = new ArrayList<>();
    private final ArrayList<String> customerAddressList = new ArrayList<>();

    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    @GetMapping("/all/names")
    public List<String> getCustomerNames() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": entered to getCustomerNames()");
        System.out.println(Thread.currentThread().getName() + ": is waiting to acquire the lock of Name");
        lock1.lock();
            System.out.println(Thread.currentThread().getName() + ": has been acquired the lock successfully of Name");
            customerNameList.clear();
            Faker faker = new Faker();
            for (int i = 0; i < 5; i++) {
                customerNameList.add(faker.name().fullName());
                Thread.sleep(customerNameList.size() % 2 == 0 ? 250 : 500);
            }
            System.out.println(Thread.currentThread().getName() + ": is about to release the lock successfully of Name");
        lock1.unlock();
        System.out.println(Thread.currentThread().getName() + ": is about to exit from getCustomerNames()");
        return (List<String>) customerNameList.clone();
    }

    @GetMapping("/all/addresses")
    public List<String> getCustomerAddresses() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": entered to getCustomerAddress()");
        System.out.println(Thread.currentThread().getName() + ": is waiting to acquire the lock of Address");
        lock2.lock();
            System.out.println(Thread.currentThread().getName() + ": has been acquired the lock successfully of Address");
            customerAddressList.clear();
            Faker faker = new Faker();
            for (int i = 0; i < 5; i++) {
                customerAddressList.add(faker.address().fullAddress());
                Thread.sleep(customerAddressList.size() % 2 == 0 ? 250 : 500);
            }
            System.out.println(Thread.currentThread().getName() + ": is about to release the lock successfully of Address");
        lock2.unlock();
        System.out.println(Thread.currentThread().getName() + ": is about to exit from getCustomerAddress()");
        return (List<String>) customerAddressList.clone();
    }
}
