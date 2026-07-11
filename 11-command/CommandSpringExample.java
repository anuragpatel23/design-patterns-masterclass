package command;

/**
 * Spring Boot: a command bus/registry. Each command has a handler bean; the bus
 * dispatches by type — the CQRS-style command pattern.
 */
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.function.Function;

interface Command<R> {}
record CreateOrderCommand(String sku, int qty) implements Command<String> {}

@Component
class CreateOrderHandler {
    public String handle(CreateOrderCommand cmd) {
        return "ORDER-" + cmd.sku() + "x" + cmd.qty();
    }
}

@Service
public class CommandSpringExample {
    private final Map<Class<?>, Function<Command<?>, ?>> handlers;

    public CommandSpringExample(CreateOrderHandler createOrder) {
        this.handlers = Map.of(
            CreateOrderCommand.class,
            (Command<?> c) -> createOrder.handle((CreateOrderCommand) c)
        );
    }

    @SuppressWarnings("unchecked")
    public <R> R dispatch(Command<R> command) {
        Function<Command<?>, ?> h = handlers.get(command.getClass());
        if (h == null) throw new IllegalArgumentException("No handler for " + command.getClass());
        return (R) h.apply(command);
    }
}
