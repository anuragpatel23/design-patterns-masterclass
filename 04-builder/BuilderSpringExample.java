package builder;

/**
 * Spring Boot: Lombok @Builder is the everyday idiom for immutable DTOs/value objects.
 * (@Value makes it immutable; @Builder.Default preserves default values.)
 */
import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class BuilderSpringExample {
    String reportType;
    String legalEntityId;
    LocalDate fromDate;
    LocalDate toDate;
    @Builder.Default boolean includeHistorical = false;
    @Builder.Default String outputFormat = "EXCEL";

    /*
     * Usage inside a @Service:
     *   BuilderSpringExample.builder()
     *       .reportType("DTP").legalEntityId("LEID-1")
     *       .fromDate(LocalDate.now().minusMonths(3))
     *       .toDate(LocalDate.now())
     *       .outputFormat("PDF")
     *       .build();
     */
}
