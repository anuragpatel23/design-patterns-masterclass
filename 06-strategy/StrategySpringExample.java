package strategy;

/**
 * Spring Boot: Map<String, Strategy> injection is the idiomatic strategy selector.
 * Each strategy registers itself as a bean; add new ones without touching the context.
 */
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

interface TaxCalculationStrategy { BigDecimal calculate(BigDecimal income); }

@Component("US")
class UsTaxStrategy implements TaxCalculationStrategy {
    public BigDecimal calculate(BigDecimal income) { return income.multiply(new BigDecimal("0.22")); }
}

@Component("UK")
class UkTaxStrategy implements TaxCalculationStrategy {
    public BigDecimal calculate(BigDecimal income) { return income.multiply(new BigDecimal("0.20")); }
}

@Service
public class StrategySpringExample {
    private final Map<String, TaxCalculationStrategy> strategies; // keyed by bean name
    public StrategySpringExample(Map<String, TaxCalculationStrategy> strategies) {
        this.strategies = strategies;
    }
    public BigDecimal calculate(String country, BigDecimal income) {
        TaxCalculationStrategy s = strategies.get(country);
        if (s == null) throw new IllegalArgumentException("No strategy for " + country);
        return s.calculate(income);
    }
}
