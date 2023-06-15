package interthread.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseCP {
    private static final int DEFAULT_SIZE = 5;
    private final List<Connection> availableConnectionList = new ArrayList<>();
    private final List<Connection> consumedConnectionList = new ArrayList<>();
    private final int size;

    public DatabaseCP() {
        this(DEFAULT_SIZE);
    }

    public DatabaseCP(int size) {
        if (size <= 0) throw new RuntimeException("Pool size can't be negative or zero");
        this.size = size;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < size; i++) {
                availableConnectionList.add(DriverManager
                        .getConnection("jdbc:mysql://localhost:3306",
                                "root", "mysql"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Connection getConnection() {
        while (availableConnectionList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Connection connection = availableConnectionList.get(0);
        consumedConnectionList.add(connection);
        availableConnectionList.remove(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        consumedConnectionList.remove(connection);
        availableConnectionList.add(connection);
        notify();
    }

    public synchronized void releaseAllConnections() {
        availableConnectionList.addAll(consumedConnectionList);
        consumedConnectionList.clear();
        notifyAll();
    }

    public Map<String, Object> getHealth() {
        Map<String, Object> poolAttributes = new LinkedHashMap<>();
        poolAttributes.put("available", availableConnectionList.size());
        poolAttributes.put("consumed", consumedConnectionList.size());
        poolAttributes.put("total", size);
        return poolAttributes;
    }
}
