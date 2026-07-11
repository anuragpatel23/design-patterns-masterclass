package factorymethod;

/**
 * Spring Boot Factory Method via Map<String, Bean> injection.
 * Add a new channel = add a new @Service; the factory picks it up automatically.
 */
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

interface NotificationService { void send(String message, String recipient); }

@Service("emailNotification")
class EmailNotificationService implements NotificationService {
    public void send(String m, String r) { System.out.println("EMAIL -> " + r + ": " + m); }
}

@Service("smsNotification")
class SmsNotificationService implements NotificationService {
    public void send(String m, String r) { System.out.println("SMS -> " + r + ": " + m); }
}

@Component
public class FactoryMethodSpringExample {

    private final Map<String, NotificationService> services;

    // Spring injects ALL NotificationService beans keyed by bean name.
    public FactoryMethodSpringExample(Map<String, NotificationService> services) {
        this.services = services;
    }

    public NotificationService resolve(String channel) {
        NotificationService svc = services.get(channel + "Notification");
        if (svc == null) throw new IllegalArgumentException("Unknown channel: " + channel);
        return svc;
    }

    public void notifyUser(String channel, String message, String recipient) {
        resolve(channel).send(message, recipient);
    }
}
