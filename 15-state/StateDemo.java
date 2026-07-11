package state;

/**
 * State — an order lifecycle where behavior and valid transitions depend on state.
 * Run: javac StateDemo.java && java state.StateDemo
 */
public class StateDemo {

    interface OrderState {
        void pay(OrderContext ctx);
        void ship(OrderContext ctx);
        String name();
    }

    static class OrderContext {
        private OrderState state = new NewState();
        void setState(OrderState s) { System.out.println("  -> " + s.name()); this.state = s; }
        void pay()  { state.pay(this); }
        void ship() { state.ship(this); }
    }

    static class NewState implements OrderState {
        public void pay(OrderContext c)  { c.setState(new PaidState()); }
        public void ship(OrderContext c) { System.out.println("  cannot ship: not paid"); }
        public String name() { return "NEW"; }
    }
    static class PaidState implements OrderState {
        public void pay(OrderContext c)  { System.out.println("  already paid"); }
        public void ship(OrderContext c) { c.setState(new ShippedState()); }
        public String name() { return "PAID"; }
    }
    static class ShippedState implements OrderState {
        public void pay(OrderContext c)  { System.out.println("  already paid & shipped"); }
        public void ship(OrderContext c) { System.out.println("  already shipped"); }
        public String name() { return "SHIPPED"; }
    }

    public static void main(String[] args) {
        OrderContext order = new OrderContext();
        order.ship(); // illegal now
        order.pay();  // NEW -> PAID
        order.ship(); // PAID -> SHIPPED
        order.ship(); // already shipped
    }
}
