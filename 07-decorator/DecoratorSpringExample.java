package decorator;

/**
 * Spring Boot: manual decorator stacking in @Configuration, plus the note that
 * @Cacheable/@Transactional are decorators applied transparently via proxies.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

interface ReportGenerator { byte[] generate(String key); }

@Service
class BaseReportGenerator implements ReportGenerator {
    public byte[] generate(String key) { return ("report:" + key).getBytes(); }
}

class LoggingReportDecorator implements ReportGenerator {
    private final ReportGenerator delegate;
    LoggingReportDecorator(ReportGenerator delegate) { this.delegate = delegate; }
    public byte[] generate(String key) {
        System.out.println("[LOG] generating " + key);
        byte[] out = delegate.generate(key);
        System.out.println("[LOG] done, " + out.length + " bytes");
        return out;
    }
}

class CachingReportDecorator implements ReportGenerator {
    private final ReportGenerator delegate;
    private final Map<String, byte[]> cache = new ConcurrentHashMap<>();
    CachingReportDecorator(ReportGenerator delegate) { this.delegate = delegate; }
    public byte[] generate(String key) { return cache.computeIfAbsent(key, delegate::generate); }
}

@Configuration
public class DecoratorSpringExample {
    // Stack: Cache -> Log -> Base. Swappable order, composable behavior.
    @Bean
    ReportGenerator reportGenerator(BaseReportGenerator base) {
        return new CachingReportDecorator(new LoggingReportDecorator(base));
    }
}
