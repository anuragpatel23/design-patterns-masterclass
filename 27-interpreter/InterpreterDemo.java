package interpreter;

/**
 * Interpreter — a tiny boolean rule language evaluated against a Customer context.
 * Run: javac InterpreterDemo.java && java interpreter.InterpreterDemo
 */
public class InterpreterDemo {

    record Customer(int totalOrders, String region) {}

    interface RuleExpression { boolean interpret(Customer c); }

    // Terminal expressions (the "vocabulary")
    static class MinOrders implements RuleExpression {
        private final int min; MinOrders(int min) { this.min = min; }
        public boolean interpret(Customer c) { return c.totalOrders() >= min; }
    }
    static class InRegion implements RuleExpression {
        private final String region; InRegion(String region) { this.region = region; }
        public boolean interpret(Customer c) { return c.region().equals(region); }
    }

    // Non-terminal expressions (the "grammar")
    static class And implements RuleExpression {
        private final RuleExpression l, r; And(RuleExpression l, RuleExpression r) { this.l = l; this.r = r; }
        public boolean interpret(Customer c) { return l.interpret(c) && r.interpret(c); }
    }
    static class Or implements RuleExpression {
        private final RuleExpression l, r; Or(RuleExpression l, RuleExpression r) { this.l = l; this.r = r; }
        public boolean interpret(Customer c) { return l.interpret(c) || r.interpret(c); }
    }

    public static void main(String[] args) {
        // Rule: (>= 10 orders) AND (region is US OR CA) — could be built from DB config.
        RuleExpression goldTier = new And(
            new MinOrders(10),
            new Or(new InRegion("US"), new InRegion("CA"))
        );
        System.out.println(goldTier.interpret(new Customer(12, "US")));  // true
        System.out.println(goldTier.interpret(new Customer(3, "US")));   // false
        System.out.println(goldTier.interpret(new Customer(20, "UK")));  // false
    }
}
