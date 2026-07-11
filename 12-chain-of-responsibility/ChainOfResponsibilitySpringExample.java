package chainofresponsibility;

/**
 * Spring Boot: an ordered list of handlers acting as a chain. Spring injects them
 * (respecting @Order); the request flows until one handles it. Conceptually the
 * same as Spring Security's FilterChain.
 */
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.List;

interface RequestHandler { boolean handle(String request); } // true = handled/stop

@Order(1)
@Component
class AuthHandler implements RequestHandler {
    public boolean handle(String req) {
        if (req.startsWith("anon:")) { System.out.println("blocked: unauthenticated"); return true; }
        return false; // pass along
    }
}

@Order(2)
@Component
class RateLimitHandler implements RequestHandler {
    public boolean handle(String req) {
        if (req.contains("flood")) { System.out.println("blocked: rate limit"); return true; }
        return false;
    }
}

@Component
public class ChainOfResponsibilitySpringExample {
    private final List<RequestHandler> chain; // injected in @Order sequence
    public ChainOfResponsibilitySpringExample(List<RequestHandler> chain) { this.chain = chain; }
    public void process(String request) {
        for (RequestHandler h : chain) if (h.handle(request)) return;
        System.out.println("processed: " + request);
    }
}
