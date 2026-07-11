package singleton;

/**
 * Spring Boot: you almost never hand-roll a Singleton — the container is one.
 * Every bean below is a singleton-scoped bean: Spring creates ONE instance and
 * injects the same reference everywhere it is needed.
 */
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service // singleton scope by default -> exactly one instance in the context
public class SingletonSpringExample {

    private final HikariDataSource dataSource; // itself a singleton bean

    // Constructor injection: Spring wires the single shared instance.
    public SingletonSpringExample(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /*
     * Interview note:
     *  - Default scope is "singleton". Use @Scope("prototype") for per-injection instances.
     *  - To verify: inject the same bean into two components and compare references — identical.
     *  - To override in tests: @MockBean replaces the singleton in the test context.
     */
}
