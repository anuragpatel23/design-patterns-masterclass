package composite;

/**
 * Spring Boot: a composite of validators. Spring injects all Validator beans;
 * the composite runs them all — treating one and many uniformly.
 */
import org.springframework.stereotype.Component;
import java.util.List;

interface OrderValidator { void validate(String order); }

@Component
class NotEmptyValidator implements OrderValidator {
    public void validate(String order) { if (order.isBlank()) throw new IllegalArgumentException("empty"); }
}

@Component
class LengthValidator implements OrderValidator {
    public void validate(String order) { if (order.length() > 100) throw new IllegalArgumentException("too long"); }
}

@Component
public class CompositeSpringExample implements OrderValidator {
    private final List<OrderValidator> validators; // all leaf validators injected

    // The @Primary/composite gathers children; clients see one OrderValidator.
    public CompositeSpringExample(List<OrderValidator> validators) {
        // exclude self to avoid recursion in real code (filter by type)
        this.validators = validators.stream().filter(v -> v != this).toList();
    }
    public void validate(String order) { validators.forEach(v -> v.validate(order)); }
}
