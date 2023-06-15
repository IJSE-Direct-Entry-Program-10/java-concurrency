package cache.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/tests")
public class VolatileHttpController {

    private volatile List<String> nameList = new ArrayList<>();
    private boolean release = false;

    @GetMapping
    public Integer getNameList() throws InterruptedException {
        release = false;
        while (!release) {
            System.out.println(nameList.size());
        }
        return nameList.size();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveNames() throws InterruptedException {
        release = false;
        while (!release) {
            nameList.add((nameList.size() + 1) + "");
        }
        System.out.println("saveNames() exited");
    }

    @DeleteMapping
    public void release() {
        release = true;
    }
}
