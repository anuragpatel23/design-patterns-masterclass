package specification;

import java.util.List;
import java.util.function.Predicate;

/**
 * Specification — composable business rules (and/or/not) reused for filtering.
 * Run: javac SpecificationDemo.java && java specification.SpecificationDemo
 */
public class SpecificationDemo {

    record Customer(String name, int orders, String region, boolean active) {}

    interface Specification<T> {
        boolean isSatisfiedBy(T candidate);
        default Specification<T> and(Specification<T> other) {
            return c -> this.isSatisfiedBy(c) && other.isSatisfiedBy(c);
        }
        default Specification<T> or(Specification<T> other) {
            return c -> this.isSatisfiedBy(c) || other.isSatisfiedBy(c);
        }
        default Specification<T> not() { return c -> !this.isSatisfiedBy(c); }
    }

    // Atomic, named, reusable rules:
    static Specification<Customer> minOrders(int n) { return c -> c.orders() >= n; }
    static Specification<Customer> inRegion(String r) { return c -> c.region().equals(r); }
    static Specification<Customer> isActive() { return Customer::active; }

    public static void main(String[] args) {
        List<Customer> customers = List.of(
            new Customer("Alice", 12, "US", true),
            new Customer("Bob", 3, "US", true),
            new Customer("Carol", 20, "UK", false),
            new Customer("Dan", 15, "US", false)
        );

        // Compose: active AND >=10 orders AND (US OR UK)
        Specification<Customer> goldEligible =
            isActive().and(minOrders(10)).and(inRegion("US").or(inRegion("UK")));

        customers.stream()
                 .filter(goldEligible::isSatisfiedBy)
                 .forEach(c -> System.out.println("Gold eligible: " + c.name()));
    }
}
