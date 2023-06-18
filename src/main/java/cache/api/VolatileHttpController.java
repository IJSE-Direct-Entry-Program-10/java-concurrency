package cache.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/v1/tests")
public class VolatileHttpController {

    private boolean release = false;

    @DeleteMapping
    public synchronized String stop() {
        release = true;
        return "<h1>Stopped</h1>";
    }

    @GetMapping
    public synchronized String start() {
        release = false;
        int i = 0;
        while (!release) {
            i++;
        }
        return "<h1>Final Test Result: " + i + "</h1>";
    }
}
