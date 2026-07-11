package flyweight;

/**
 * Spring Boot: @Cacheable makes Spring share the result of an expensive computation
 * across all callers with the same key — a flyweight-style sharing of heavy objects.
 */
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FlyweightSpringExample {

    // First call computes & caches; subsequent calls with same currency share the result.
    @Cacheable("exchangeRates")
    public double rateFor(String currency) {
        simulateExpensiveLookup();
        return switch (currency) { case "EUR" -> 1.08; case "GBP" -> 1.27; default -> 1.0; };
    }

    private void simulateExpensiveLookup() {
        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
    }
}
