package visitor;

/**
 * Spring Boot: a report-export visitor. Domain nodes accept different export visitors
 * (PDF, CSV) as beans, so new export formats don't modify the domain classes.
 */
import org.springframework.stereotype.Component;

interface ExportVisitor { String visitInvoice(Invoice i); String visitReceipt(Receipt r); }
interface Exportable { String export(ExportVisitor v); }

record Invoice(String id, long total) implements Exportable {
    public String export(ExportVisitor v) { return v.visitInvoice(this); }
}
record Receipt(String id) implements Exportable {
    public String export(ExportVisitor v) { return v.visitReceipt(this); }
}

@Component
public class VisitorSpringExample implements ExportVisitor {
    public String visitInvoice(Invoice i) { return "PDF<invoice " + i.id() + " $" + i.total() + ">"; }
    public String visitReceipt(Receipt r) { return "PDF<receipt " + r.id() + ">"; }
    // Add CsvExportVisitor as another @Component without touching Invoice/Receipt.
}
