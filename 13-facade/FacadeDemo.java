package facade;

/**
 * Facade — an OrderFacade coordinating inventory, payment, and shipping subsystems.
 * Run: javac FacadeDemo.java && java facade.FacadeDemo
 */
public class FacadeDemo {

    // --- Complex subsystem parts ---
    static class InventoryService {
        boolean reserve(String sku, int qty) { System.out.println("Reserved " + qty + " of " + sku); return true; }
    }
    static class PaymentService {
        String charge(String account, long cents) { System.out.println("Charged " + cents + "c to " + account); return "PAY-OK"; }
    }
    static class ShippingService {
        String schedule(String sku, String address) { System.out.println("Shipping " + sku + " to " + address); return "TRACK-123"; }
    }

    // --- Facade: one simple call orchestrates the subsystem ---
    static class OrderFacade {
        private final InventoryService inventory = new InventoryService();
        private final PaymentService payment = new PaymentService();
        private final ShippingService shipping = new ShippingService();

        String placeOrder(String sku, int qty, String account, long cents, String address) {
            if (!inventory.reserve(sku, qty)) throw new IllegalStateException("Out of stock");
            String pay = payment.charge(account, cents);
            if (!"PAY-OK".equals(pay)) throw new IllegalStateException("Payment failed");
            return shipping.schedule(sku, address);
        }
    }

    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();
        String tracking = facade.placeOrder("BOOK-42", 1, "cus_1", 1999, "1 Infinite Loop");
        System.out.println("Order complete. Tracking: " + tracking);
    }
}
