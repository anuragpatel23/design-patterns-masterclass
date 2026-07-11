package objectpool;

/**
 * Spring Boot: HikariCP IS the object pool. You configure it; Spring Boot auto-wires it.
 * You rarely implement pooling yourself — you tune the existing pool.
 */
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ObjectPoolSpringExample {

    private final HikariDataSource pool; // configured via spring.datasource.hikari.*

    public ObjectPoolSpringExample(HikariDataSource pool) { this.pool = pool; }

    public String runQuery(String sql) throws SQLException {
        // try-with-resources RETURNS the connection to the pool (does not physically close it)
        try (Connection borrowed = pool.getConnection()) {
            return "Borrowed " + borrowed + " from pool of size "
                    + pool.getHikariPoolMXBean().getTotalConnections();
        }
    }
    /*
     * Key tuning knobs (application.yml):
     *   spring.datasource.hikari.maximum-pool-size, minimum-idle,
     *   connection-timeout, idle-timeout, max-lifetime
     */
}
