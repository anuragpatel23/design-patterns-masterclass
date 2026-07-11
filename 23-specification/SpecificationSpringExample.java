package specification;

/**
 * Spring Boot: Spring Data Specification<T> composes predicates that translate to a
 * single SQL query via JpaSpecificationExecutor — write the rule once, run it in the DB.
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Entity
class Customer {
    @Id Long id;
    String region;
    int orders;
    boolean active;
}

interface CustomerRepo extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {}

public class SpecificationSpringExample {

    // Atomic specs translate to WHERE clauses:
    static Specification<Customer> inRegion(String region) {
        return (root, query, cb) -> cb.equal(root.get("region"), region);
    }
    static Specification<Customer> minOrders(int n) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("orders"), n);
    }
    static Specification<Customer> active() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    // Usage: repo.findAll(inRegion("US").and(minOrders(10)).and(active()));
    // -> ONE SQL query with a composed WHERE clause.
}
