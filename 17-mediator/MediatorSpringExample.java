package mediator;

/**
 * Spring Boot: ApplicationEventPublisher acts as an in-process mediator. Colleagues
 * don't reference each other; they publish/handle events through the central hub.
 */
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

record UserRegistered(String email) {}

@Service
class MediatorSpringExample {
    private final ApplicationEventPublisher mediator;
    MediatorSpringExample(ApplicationEventPublisher mediator) { this.mediator = mediator; }
    public void register(String email) {
        // No direct calls to EmailService, CrmService, etc. — the mediator coordinates.
        mediator.publishEvent(new UserRegistered(email));
    }
}

@Component
class WelcomeEmailColleague {
    @EventListener void on(UserRegistered e) { System.out.println("welcome email -> " + e.email()); }
}

@Component
class CrmSyncColleague {
    @EventListener void on(UserRegistered e) { System.out.println("sync CRM -> " + e.email()); }
}
