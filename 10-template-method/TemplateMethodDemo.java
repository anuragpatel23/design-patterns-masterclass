package templatemethod;

/**
 * Template Method — a fixed data-import skeleton with subclass-specific parse/validate.
 * Run: javac TemplateMethodDemo.java && java templatemethod.TemplateMethodDemo
 */
public class TemplateMethodDemo {

    static abstract class DataImporter {
        // The template method fixes the sequence; it is final so it can't be overridden.
        public final void importData(String source) {
            String raw = read(source);
            Object parsed = parse(raw);
            validate(parsed);
            save(parsed);
            afterImport();          // hook with a default (optional override)
        }
        protected String read(String source) { return "raw(" + source + ")"; }
        protected abstract Object parse(String raw);
        protected abstract void validate(Object parsed);
        protected void save(Object parsed) { System.out.println("Saved: " + parsed); }
        protected void afterImport() { /* default no-op hook */ }
    }

    static class CsvImporter extends DataImporter {
        protected Object parse(String raw) { System.out.println("Parsing CSV"); return "[csv-rows]"; }
        protected void validate(Object parsed) { System.out.println("Validating CSV schema"); }
    }
    static class JsonImporter extends DataImporter {
        protected Object parse(String raw) { System.out.println("Parsing JSON"); return "{json}"; }
        protected void validate(Object parsed) { System.out.println("Validating JSON schema"); }
        @Override protected void afterImport() { System.out.println("JSON import: reindexing"); }
    }

    public static void main(String[] args) {
        new CsvImporter().importData("customers.csv");
        System.out.println("---");
        new JsonImporter().importData("events.json");
    }
}
