package interthread.api;

import interthread.db.DatabaseCP;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/v1/pools/db")
public class PoolHttpController {

    private final DatabaseCP dbcp = new DatabaseCP(3);
    private final ConcurrentHashMap<Integer, Connection> connectionMap = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private int id = 0;

    @GetMapping("/connections/available")
    public Map<String, Object> acquireConnection(){
        Connection connection = dbcp.getConnection();
        lock.lock();
            connectionMap.put(++id, connection);
        lock.unlock();
        Map<String, Object> props = new LinkedHashMap<>();
        props.put("id", id);
        props.put("connection", connection.toString());
        return props;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/connections/{connectionId}")
    public void releaseConnection(@PathVariable Integer connectionId){
        if (!connectionMap.containsKey(connectionId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Connection ID");

        Connection connection = connectionMap.remove(connectionId);
        dbcp.releaseConnection(connection);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/connections")
    public void releaseAllConnections(){
        connectionMap.clear();
        dbcp.releaseAllConnections();
        id = 0;
    }

    @GetMapping
    public Map<String, Object> checkHealth(){
        return dbcp.getHealth();
    }
}
