package state;

/**
 * Spring Boot: a state machine expressed with a legal-transition map inside a service.
 * (For complex workflows, use the spring-statemachine library.)
 */
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;

enum OrderStatus { NEW, PAID, SHIPPED, CANCELLED }

@Service
public class StateSpringExample {
    // Explicit, enforceable transition table.
    private static final Map<OrderStatus, Set<OrderStatus>> LEGAL = Map.of(
        OrderStatus.NEW,     Set.of(OrderStatus.PAID, OrderStatus.CANCELLED),
        OrderStatus.PAID,    Set.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED),
        OrderStatus.SHIPPED, Set.of(),
        OrderStatus.CANCELLED, Set.of()
    );

    public OrderStatus transition(OrderStatus from, OrderStatus to) {
        if (!LEGAL.getOrDefault(from, Set.of()).contains(to))
            throw new IllegalStateException("Illegal transition " + from + " -> " + to);
        return to;
    }
}
