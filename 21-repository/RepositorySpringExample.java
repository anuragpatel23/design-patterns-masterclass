package repository;

/**
 * Spring Boot: declare an interface; Spring Data generates the implementation.
 * Query methods are derived from method names or @Query.
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Entity
class Customer {
    @Id Long id;
    String name;
    String region;
}

public interface RepositorySpringExample extends JpaRepository<Customer, Long> {
    // Derived query — no implementation needed.
    List<Customer> findByRegion(String region);

    // For clean architecture, wrap this behind a domain-facing port interface and
    // inject the port into your domain services so the domain never sees JPA.
}
