package templatemethod;

/**
 * Spring Boot: JdbcTemplate is Template Method in action — it owns connect/execute/
 * handle-exceptions/close; you supply only the varying RowMapper callback.
 */
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

record Customer(long id, String name) {}

@Repository
public class TemplateMethodSpringExample {
    private final JdbcTemplate jdbc;
    public TemplateMethodSpringExample(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Customer> findAll() {
        // JdbcTemplate runs the fixed skeleton; we fill in only the row mapping.
        return jdbc.query("SELECT id, name FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("name")));
    }
}
