package nullobject;

import java.util.Map;

/**
 * Null Object — a customer whose "no discount" policy is a safe no-op object,
 * so callers never null-check the discount policy.
 * Run: javac NullObjectDemo.java && java nullobject.NullObjectDemo
 */
public class NullObjectDemo {

    interface DiscountPolicy { long applyTo(long cents); }

    static class PercentDiscount implements DiscountPolicy {
        private final int percent; PercentDiscount(int percent) { this.percent = percent; }
        public long applyTo(long cents) { return cents - (cents * percent / 100); }
    }

    // The Null Object: neutral behavior (no discount), never null.
    static final class NoDiscount implements DiscountPolicy {
        static final NoDiscount INSTANCE = new NoDiscount();
        public long applyTo(long cents) { return cents; }
    }

    static class PricingService {
        private final Map<String, DiscountPolicy> byTier =
                Map.of("GOLD", new PercentDiscount(20), "SILVER", new PercentDiscount(10));
        long price(String tier, long cents) {
            // No null check needed: unknown tiers get the safe Null Object.
            return byTier.getOrDefault(tier, NoDiscount.INSTANCE).applyTo(cents);
        }
    }

    public static void main(String[] args) {
        PricingService svc = new PricingService();
        System.out.println("GOLD:    " + svc.price("GOLD", 10_000));
        System.out.println("BRONZE:  " + svc.price("BRONZE", 10_000)); // no discount, no NPE
    }
}
