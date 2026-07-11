package strategy;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Strategy — country-specific tax calculators selected at runtime, no if/else chain.
 * Run: javac StrategyDemo.java && java strategy.StrategyDemo
 */
public class StrategyDemo {

    interface TaxStrategy { BigDecimal calculate(BigDecimal income); }

    static class UsTax implements TaxStrategy {
        public BigDecimal calculate(BigDecimal income) {
            return income.compareTo(new BigDecimal("44725")) <= 0
                    ? income.multiply(new BigDecimal("0.12"))
                    : income.multiply(new BigDecimal("0.22"));
        }
    }
    static class UkTax implements TaxStrategy {
        public BigDecimal calculate(BigDecimal income) {
            BigDecimal allowance = new BigDecimal("12570");
            return income.compareTo(allowance) <= 0 ? BigDecimal.ZERO
                    : income.subtract(allowance).multiply(new BigDecimal("0.20"));
        }
    }

    static class TaxContext {
        private final Map<String, TaxStrategy> strategies =
                Map.of("US", new UsTax(), "UK", new UkTax());
        BigDecimal compute(String country, BigDecimal income) {
            TaxStrategy s = strategies.get(country);
            if (s == null) throw new IllegalArgumentException("No strategy for " + country);
            return s.calculate(income);
        }
    }

    public static void main(String[] args) {
        TaxContext ctx = new TaxContext();
        System.out.println("US tax on 50000 = " + ctx.compute("US", new BigDecimal("50000")));
        System.out.println("UK tax on 50000 = " + ctx.compute("UK", new BigDecimal("50000")));
    }
}
