package proxy;

/**
 * Spring Boot: @Transactional makes Spring wrap this bean in a proxy that opens
 * a transaction, delegates to the real method, then commits/rolls back.
 */
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProxySpringExample {

    @Transactional // proxy opens tx -> calls real method -> commits or rolls back
    public void transferFunds(String from, String to, long amount) {
        debit(from, amount);
        credit(to, amount);
        // If either throws a RuntimeException, the proxy rolls the whole thing back.
    }

    private void debit(String acct, long amt)  { /* ... */ }
    private void credit(String acct, long amt) { /* ... */ }

    /*
     * SELF-INVOCATION GOTCHA:
     *   public void a() { this.b(); }   // b()'s @Transactional is IGNORED
     *   @Transactional public void b() { ... }
     * Because this.b() bypasses the proxy. Inject the bean and call it, or split beans.
     */
}
