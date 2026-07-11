package repository;

import java.util.*;

/**
 * Repository — a domain-facing, collection-like abstraction backed by an in-memory
 * store here (swap for JPA/JDBC without touching the domain).
 * Run: javac RepositoryDemo.java && java repository.RepositoryDemo
 */
public class RepositoryDemo {

    record Customer(Long id, String name, String region) {}

    // Domain-facing port: speaks the domain's language, hides the storage tech.
    interface CustomerRepository {
        Optional<Customer> findById(Long id);
        List<Customer> findByRegion(String region);
        Customer save(Customer c);
    }

    // Infrastructure adapter (in-memory here; could be JPA/JDBC/Mongo).
    static class InMemoryCustomerRepository implements CustomerRepository {
        private final Map<Long, Customer> store = new HashMap<>();
        public Optional<Customer> findById(Long id) { return Optional.ofNullable(store.get(id)); }
        public List<Customer> findByRegion(String region) {
            return store.values().stream().filter(c -> c.region().equals(region)).toList();
        }
        public Customer save(Customer c) { store.put(c.id(), c); return c; }
    }

    // Domain service depends only on the interface -> trivially unit-testable.
    static class OnboardingService {
        private final CustomerRepository repo;
        OnboardingService(CustomerRepository repo) { this.repo = repo; }
        Customer onboard(Long id, String name, String region) { return repo.save(new Customer(id, name, region)); }
    }

    public static void main(String[] args) {
        CustomerRepository repo = new InMemoryCustomerRepository();
        OnboardingService svc = new OnboardingService(repo);
        svc.onboard(1L, "Alice", "US");
        svc.onboard(2L, "Bob", "US");
        svc.onboard(3L, "Carol", "UK");
        System.out.println("US customers: " + repo.findByRegion("US"));
        System.out.println("Find #3: " + repo.findById(3L).orElseThrow());
    }
}
