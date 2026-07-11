package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer — an order subject notifying inventory, email, and analytics observers.
 * Run: javac ObserverDemo.java && java observer.ObserverDemo
 */
public class ObserverDemo {

    interface OrderObserver { void onOrderPlaced(String orderId, long amount); }

    static class OrderSubject {
        private final List<OrderObserver> observers = new ArrayList<>();
        void subscribe(OrderObserver o) { observers.add(o); }
        void unsubscribe(OrderObserver o) { observers.remove(o); }
        void placeOrder(String orderId, long amount) {
            System.out.println("Order placed: " + orderId);
            // Notify all; isolate observer failures so one bad listener can't break the rest.
            for (OrderObserver o : observers) {
                try { o.onOrderPlaced(orderId, amount); }
                catch (RuntimeException ex) { System.out.println("Observer failed: " + ex.getMessage()); }
            }
        }
    }

    public static void main(String[] args) {
        OrderSubject subject = new OrderSubject();
        subject.subscribe((id, amt) -> System.out.println("  [Inventory] decrement stock for " + id));
        subject.subscribe((id, amt) -> System.out.println("  [Email] send receipt for " + id));
        subject.subscribe((id, amt) -> System.out.println("  [Analytics] record $" + amt));
        subject.placeOrder("ORD-1001", 4599);
    }
}
