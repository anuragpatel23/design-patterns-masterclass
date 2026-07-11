package nullobject;

/**
 * Spring Boot: supply a no-op bean as the safe default so collaborators never null-check.
 * Optional<T> injection + orElse(noOp) is a common variant.
 */
import org.springframework.stereotype.Component;

interface AuditLogger { void log(String event); }

@Component
class NoOpAuditLogger implements AuditLogger {   // Null Object bean
    public void log(String event) { /* intentionally does nothing */ }
}

@Component
public class NullObjectSpringExample {
    private final AuditLogger auditLogger;
    // If no real AuditLogger is configured, the no-op bean is injected -> no null checks.
    public NullObjectSpringExample(AuditLogger auditLogger) { this.auditLogger = auditLogger; }
    public void doWork() { auditLogger.log("work done"); } // always safe to call
}
