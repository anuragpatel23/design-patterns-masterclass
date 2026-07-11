package builder;

import java.time.LocalDate;

/**
 * Builder — an immutable ReportRequest with required + optional fields and validation.
 * Run: javac BuilderDemo.java && java builder.BuilderDemo
 */
public class BuilderDemo {

    // Immutable product
    static final class ReportRequest {
        private final String reportType;      // required
        private final String legalEntityId;   // required
        private final LocalDate fromDate;     // optional (defaulted)
        private final LocalDate toDate;       // optional (defaulted)
        private final boolean includeHistorical;
        private final String outputFormat;

        private ReportRequest(Builder b) {
            this.reportType = b.reportType;
            this.legalEntityId = b.legalEntityId;
            this.fromDate = b.fromDate;
            this.toDate = b.toDate;
            this.includeHistorical = b.includeHistorical;
            this.outputFormat = b.outputFormat;
        }

        @Override public String toString() {
            return "ReportRequest{" + reportType + ", " + legalEntityId + ", " + fromDate +
                   " -> " + toDate + ", historical=" + includeHistorical + ", " + outputFormat + "}";
        }

        static final class Builder {
            private final String reportType;      // required via constructor
            private final String legalEntityId;
            private LocalDate fromDate = LocalDate.now().minusMonths(1);
            private LocalDate toDate = LocalDate.now();
            private boolean includeHistorical = false;
            private String outputFormat = "EXCEL";

            Builder(String reportType, String legalEntityId) {
                this.reportType = reportType;
                this.legalEntityId = legalEntityId;
            }
            Builder fromDate(LocalDate d)          { this.fromDate = d; return this; }
            Builder toDate(LocalDate d)            { this.toDate = d; return this; }
            Builder includeHistorical(boolean b)   { this.includeHistorical = b; return this; }
            Builder outputFormat(String f)         { this.outputFormat = f; return this; }

            ReportRequest build() {
                if (fromDate.isAfter(toDate))
                    throw new IllegalArgumentException("fromDate cannot be after toDate");
                return new ReportRequest(this);
            }
        }
    }

    public static void main(String[] args) {
        ReportRequest r = new ReportRequest.Builder("DTP", "LEID-12345")
                .fromDate(LocalDate.of(2025, 1, 1))
                .toDate(LocalDate.of(2025, 12, 31))
                .includeHistorical(true)
                .outputFormat("PDF")
                .build();
        System.out.println(r);

        // Optional fields fall back to sensible defaults:
        ReportRequest minimal = new ReportRequest.Builder("SUMMARY", "LEID-99999").build();
        System.out.println(minimal);
    }
}
