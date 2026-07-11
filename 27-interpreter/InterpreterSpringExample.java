package interpreter;

/**
 * Spring Boot: SpEL is the framework-provided interpreter. Store a rule as a String
 * (in config or DB) and evaluate it against a context object at runtime — no redeploy.
 */
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

@Service
public class InterpreterSpringExample {

    private final ExpressionParser parser = new SpelExpressionParser();

    public record Customer(int totalOrders, String region) {}

    // ruleText comes from configuration/DB, e.g.:
    //   "totalOrders >= 10 and (region == 'US' or region == 'CA')"
    public boolean evaluate(String ruleText, Customer customer) {
        Expression expr = parser.parseExpression(ruleText);
        return Boolean.TRUE.equals(expr.getValue(customer, Boolean.class));
    }
}
