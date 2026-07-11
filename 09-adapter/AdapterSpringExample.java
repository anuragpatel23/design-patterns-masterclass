package adapter;

/**
 * Spring Boot: @FeignClient adapts a remote REST API into a plain Java interface.
 * Your services call methods; Feign translates them into HTTP calls.
 */
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// The adapter: a remote HTTP API exposed as a local interface.
@FeignClient(name = "exchange-rates", url = "${rates.api.url}")
interface ExchangeRateClient {
    @GetMapping("/latest/{base}")
    String latestRates(@PathVariable String base);
}

@Service
public class AdapterSpringExample {
    private final ExchangeRateClient client; // looks local; is remote

    public AdapterSpringExample(ExchangeRateClient client) { this.client = client; }

    public String ratesFor(String base) {
        // Domain code is oblivious to HTTP, serialization, retries, etc.
        return client.latestRates(base);
    }
}
