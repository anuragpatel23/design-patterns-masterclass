package decorator;

/**
 * Decorator — a coffee order where wrappers add cost and description at runtime.
 * Run: javac DecoratorDemo.java && java decorator.DecoratorDemo
 */
public class DecoratorDemo {

    interface Beverage { String description(); double cost(); }

    // Concrete component
    static class Espresso implements Beverage {
        public String description() { return "Espresso"; }
        public double cost() { return 2.00; }
    }

    // Base decorator: same interface, wraps a Beverage.
    static abstract class AddOn implements Beverage {
        protected final Beverage base;
        AddOn(Beverage base) { this.base = base; }
    }
    static class Milk extends AddOn {
        Milk(Beverage b) { super(b); }
        public String description() { return base.description() + " + Milk"; }
        public double cost() { return base.cost() + 0.50; }
    }
    static class WhippedCream extends AddOn {
        WhippedCream(Beverage b) { super(b); }
        public String description() { return base.description() + " + Whipped Cream"; }
        public double cost() { return base.cost() + 0.70; }
    }
    static class Caramel extends AddOn {
        Caramel(Beverage b) { super(b); }
        public String description() { return base.description() + " + Caramel"; }
        public double cost() { return base.cost() + 0.60; }
    }

    public static void main(String[] args) {
        Beverage drink = new Caramel(new WhippedCream(new Milk(new Espresso())));
        System.out.printf("%s = $%.2f%n", drink.description(), drink.cost());
        // Order is composable: a plain espresso stays cheap and simple.
        Beverage plain = new Espresso();
        System.out.printf("%s = $%.2f%n", plain.description(), plain.cost());
    }
}
