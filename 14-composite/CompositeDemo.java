package composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite — a file system where files (leaves) and directories (composites)
 * share one interface and size() recurses over the tree.
 * Run: javac CompositeDemo.java && java composite.CompositeDemo
 */
public class CompositeDemo {

    interface Node { String name(); long size(); void print(String indent); }

    // Leaf
    static class FileNode implements Node {
        private final String name; private final long bytes;
        FileNode(String name, long bytes) { this.name = name; this.bytes = bytes; }
        public String name() { return name; }
        public long size() { return bytes; }
        public void print(String indent) { System.out.println(indent + "- " + name + " (" + bytes + "b)"); }
    }

    // Composite
    static class DirNode implements Node {
        private final String name;
        private final List<Node> children = new ArrayList<>();
        DirNode(String name) { this.name = name; }
        DirNode add(Node child) { children.add(child); return this; }
        public String name() { return name; }
        public long size() { return children.stream().mapToLong(Node::size).sum(); } // recursion
        public void print(String indent) {
            System.out.println(indent + "+ " + name + "/ (" + size() + "b)");
            children.forEach(c -> c.print(indent + "  "));
        }
    }

    public static void main(String[] args) {
        DirNode root = new DirNode("root")
            .add(new FileNode("readme.md", 1200))
            .add(new DirNode("src")
                .add(new FileNode("Main.java", 3400))
                .add(new FileNode("Util.java", 800)));
        root.print("");
        System.out.println("Total size = " + root.size() + " bytes");
    }
}
