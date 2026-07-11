package observer;

/**
 * Spring Boot: ApplicationEventPublisher + @EventListener implement Observer.
 * @Async makes analytics non-blocking.
 */
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

record OrderPlacedEvent(String orderId, long amount) {}

@Service
class ObserverSpringExample {
    private final ApplicationEventPublisher publisher;
    ObserverSpringExample(ApplicationEventPublisher publisher) { this.publisher = publisher; }
    public void placeOrder(String orderId, long amount) {
        // ... persist order ...
        publisher.publishEvent(new OrderPlacedEvent(orderId, amount)); // notify observers
    }
}

@Component
class InventoryListener {
    @EventListener
    void on(OrderPlacedEvent e) { System.out.println("decrement stock for " + e.orderId()); }
}

@Component
class AnalyticsListener {
    @Async // non-blocking observer
    @EventListener
    void on(OrderPlacedEvent e) { System.out.println("record $" + e.amount()); }
}
