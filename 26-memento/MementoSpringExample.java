package memento;

/**
 * Spring Boot: checkpoint a multi-step workflow so it can roll back to a prior state
 * (conceptually the same as saga compensating-state snapshots).
 */
import org.springframework.stereotype.Service;
import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class MementoSpringExample {

    public record WorkflowSnapshot(String step, String payload) {} // opaque to callers

    private final Deque<WorkflowSnapshot> checkpoints = new ArrayDeque<>();

    public void checkpoint(String step, String payload) {
        checkpoints.push(new WorkflowSnapshot(step, payload));
    }

    public WorkflowSnapshot rollback() {
        if (checkpoints.isEmpty()) throw new IllegalStateException("nothing to roll back");
        return checkpoints.pop(); // restore previous workflow state
    }
}
