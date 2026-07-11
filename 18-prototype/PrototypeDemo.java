package prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Prototype — deep-copy via copy constructor (preferred over Object.clone()).
 * Run: javac PrototypeDemo.java && java prototype.PrototypeDemo
 */
public class PrototypeDemo {

    static class Document {
        private String title;
        private final List<String> sections;   // mutable inner state -> must deep copy

        Document(String title, List<String> sections) {
            this.title = title;
            this.sections = new ArrayList<>(sections);
        }
        // Copy constructor performs a DEEP copy of the section list.
        Document(Document other) {
            this.title = other.title;
            this.sections = new ArrayList<>(other.sections);
        }
        Document copy() { return new Document(this); }

        void setTitle(String t) { this.title = t; }
        void addSection(String s) { sections.add(s); }
        @Override public String toString() { return title + " -> " + sections; }
    }

    public static void main(String[] args) {
        Document template = new Document("TEMPLATE",
                List.of("Header", "Body", "Footer"));

        // Clone the expensive-to-build template, then customize the copy.
        Document invoice = template.copy();
        invoice.setTitle("Invoice #1001");
        invoice.addSection("Line Items");

        System.out.println("Original:  " + template); // unchanged -> deep copy worked
        System.out.println("Clone:     " + invoice);
        System.out.println("Independent? " + (template.toString().equals(
                new Document("TEMPLATE", List.of("Header", "Body", "Footer")).toString())));
    }
}
