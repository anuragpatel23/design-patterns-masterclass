package abstractfactory;

/**
 * Abstract Factory — a cross-platform UI toolkit producing matched widget families.
 * Run: javac AbstractFactoryDemo.java && java abstractfactory.AbstractFactoryDemo
 */
public class AbstractFactoryDemo {

    // Abstract products
    interface Button   { String render(); }
    interface Checkbox { String render(); }

    // Concrete products — "Light" family
    static class LightButton   implements Button   { public String render() { return "[ Light Button ]"; } }
    static class LightCheckbox implements Checkbox { public String render() { return "[x] Light Checkbox"; } }

    // Concrete products — "Dark" family
    static class DarkButton    implements Button   { public String render() { return "( Dark Button )"; } }
    static class DarkCheckbox  implements Checkbox { public String render() { return "(x) Dark Checkbox"; } }

    // Abstract factory
    interface ThemeFactory { Button createButton(); Checkbox createCheckbox(); }

    // Concrete factories — each yields a consistent family
    static class LightThemeFactory implements ThemeFactory {
        public Button   createButton()   { return new LightButton(); }
        public Checkbox createCheckbox() { return new LightCheckbox(); }
    }
    static class DarkThemeFactory implements ThemeFactory {
        public Button   createButton()   { return new DarkButton(); }
        public Checkbox createCheckbox() { return new DarkCheckbox(); }
    }

    // Client depends only on the abstract factory + abstract products.
    static void renderScreen(ThemeFactory factory) {
        System.out.println(factory.createButton().render());
        System.out.println(factory.createCheckbox().render());
    }

    public static void main(String[] args) {
        System.out.println("--- Light theme ---");
        renderScreen(new LightThemeFactory());
        System.out.println("--- Dark theme ---");
        renderScreen(new DarkThemeFactory());
    }
}
