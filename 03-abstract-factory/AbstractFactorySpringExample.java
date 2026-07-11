package abstractfactory;

/**
 * Spring Boot: choose an entire persistence family via @ConditionalOnProperty.
 * Set db.type=postgres or db.type=h2 to swap the whole matched set of beans.
 */
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

interface PersistenceFactory {
    DataSource createDataSource();
    String dialect();
}

@Component
@ConditionalOnProperty(name = "db.type", havingValue = "postgres")
class PostgresPersistenceFactory implements PersistenceFactory {
    public DataSource createDataSource() { /* build Hikari for Postgres */ return null; }
    public String dialect() { return "org.hibernate.dialect.PostgreSQLDialect"; }
}

@Component
@ConditionalOnProperty(name = "db.type", havingValue = "h2")
class H2PersistenceFactory implements PersistenceFactory {
    public DataSource createDataSource() { /* build Hikari for H2 */ return null; }
    public String dialect() { return "org.hibernate.dialect.H2Dialect"; }
}

@Component
public class AbstractFactorySpringExample {
    private final PersistenceFactory factory; // exactly one wins, based on config
    public AbstractFactorySpringExample(PersistenceFactory factory) { this.factory = factory; }
    public String activeDialect() { return factory.dialect(); }
}
