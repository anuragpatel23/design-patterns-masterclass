package proxy;

import java.util.Set;

/**
 * Proxy — a protection proxy that checks a role, plus a virtual (lazy) proxy.
 * Run: javac ProxyDemo.java && java proxy.ProxyDemo
 */
public class ProxyDemo {

    interface Document { String read(); }

    // Real subject: pretend it is expensive to load.
    static class RealDocument implements Document {
        private final String content;
        RealDocument(String name) {
            System.out.println("[RealDocument] expensive load of " + name);
            this.content = "Confidential contents of " + name;
        }
        public String read() { return content; }
    }

    // Virtual proxy: create RealDocument only on first read.
    static class LazyDocument implements Document {
        private final String name;
        private RealDocument real;
        LazyDocument(String name) { this.name = name; }
        public String read() {
            if (real == null) real = new RealDocument(name);
            return real.read();
        }
    }

    // Protection proxy: enforce access control before delegating.
    static class SecuredDocument implements Document {
        private final Document delegate;
        private final Set<String> roles;
        SecuredDocument(Document delegate, Set<String> roles) {
            this.delegate = delegate; this.roles = roles;
        }
        public String read() {
            if (!roles.contains("READER"))
                throw new SecurityException("Access denied");
            return delegate.read();
        }
    }

    public static void main(String[] args) {
        Document lazy = new LazyDocument("Q4-Report.pdf");
        System.out.println("Created proxy; nothing loaded yet.");
        System.out.println(lazy.read());   // triggers the expensive load now

        Document secured = new SecuredDocument(lazy, Set.of("READER"));
        System.out.println(secured.read());

        try {
            new SecuredDocument(lazy, Set.of("GUEST")).read();
        } catch (SecurityException e) {
            System.out.println("Blocked: " + e.getMessage());
        }
    }
}
