package factorymethod;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory Method — a logistics company choosing a transport at runtime.
 * Run: javac FactoryMethodDemo.java && java factorymethod.FactoryMethodDemo
 */
public class FactoryMethodDemo {

    // Product
    interface Transport { String deliver(String cargo); }

    // Concrete products
    static class Truck implements Transport {
        public String deliver(String cargo) { return "Delivering '" + cargo + "' by road in a truck."; }
    }
    static class Ship implements Transport {
        public String deliver(String cargo) { return "Delivering '" + cargo + "' by sea in a ship."; }
    }
    static class Drone implements Transport {
        public String deliver(String cargo) { return "Delivering '" + cargo + "' by air with a drone."; }
    }

    // Factory: a registry-based factory scales better than a switch.
    static class LogisticsFactory {
        private final Map<String, Supplier<Transport>> registry = Map.of(
            "ROAD", Truck::new,
            "SEA",  Ship::new,
            "AIR",  Drone::new
        );
        public Transport create(String mode) {
            Supplier<Transport> s = registry.get(mode.toUpperCase());
            if (s == null) throw new IllegalArgumentException("Unknown transport mode: " + mode);
            return s.get();
        }
    }

    public static void main(String[] args) {
        LogisticsFactory factory = new LogisticsFactory();
        for (String mode : new String[]{"ROAD", "SEA", "AIR"}) {
            Transport t = factory.create(mode);
            System.out.println(t.deliver("Server rack #42"));
        }
    }
}
