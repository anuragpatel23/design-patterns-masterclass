package prototype;

/**
 * Spring Boot: @Scope("prototype") gives a brand-new bean per request/injection,
 * as opposed to the default shared singleton.
 */
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // new instance every time it is requested
public class PrototypeSpringExample {

    private final long instanceId = System.nanoTime();
    public long getInstanceId() { return instanceId; }

    /*
     * Gotcha: injecting a prototype bean into a singleton captures ONE instance.
     * To get a fresh prototype each call, use ObjectProvider<PrototypeSpringExample>
     * or @Lookup method injection.
     */
}
