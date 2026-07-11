package facade;

/**
 * Spring Boot: the @Service layer as a facade over repositories and external clients.
 * Controllers depend on one intention-revealing method, not on internals.
 */
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacadeSpringExample {

    // In real code these are injected repositories / clients.
    // private final OrderRepository orders;
    // private final PaymentClient payments;
    // private final ShippingClient shipping;

    @Transactional
    public String placeOrder(String sku, int qty, String account, long cents, String address) {
        // orders.reserve(...); payments.charge(...); shipping.schedule(...);
        // The web layer just calls placeOrder(...) and stays ignorant of the steps.
        return "TRACK-123";
    }
}
