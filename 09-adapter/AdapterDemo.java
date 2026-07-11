package adapter;

/**
 * Adapter — make an incompatible third-party payment SDK fit our PaymentProcessor interface.
 * Run: javac AdapterDemo.java && java adapter.AdapterDemo
 */
public class AdapterDemo {

    // Target: the interface OUR code expects.
    interface PaymentProcessor { boolean pay(String account, long cents); }

    // Adaptee: a third-party SDK we cannot modify, with an incompatible API.
    static class LegacyStripeSdk {
        // amount in dollars as double, different method name/shape
        String charge(double dollars, String customerToken) {
            return "stripe_txn_" + customerToken + "_" + dollars;
        }
    }

    // Adapter: implements Target, translates to the Adaptee.
    static class StripeAdapter implements PaymentProcessor {
        private final LegacyStripeSdk sdk;
        StripeAdapter(LegacyStripeSdk sdk) { this.sdk = sdk; }
        public boolean pay(String account, long cents) {
            double dollars = cents / 100.0;                 // translate units
            String result = sdk.charge(dollars, account);   // translate method
            System.out.println("Adapted call -> " + result);
            return result.startsWith("stripe_txn_");
        }
    }

    public static void main(String[] args) {
        PaymentProcessor processor = new StripeAdapter(new LegacyStripeSdk());
        // Client speaks only the Target interface; the SDK's shape is hidden.
        boolean ok = processor.pay("cus_ABC123", 4599);
        System.out.println("Payment success? " + ok);
    }
}
