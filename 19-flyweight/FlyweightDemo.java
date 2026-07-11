package flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight — thousands of trees on a map share a few TreeType flyweights.
 * Intrinsic (name, color, texture) shared; extrinsic (x, y) passed in.
 * Run: javac FlyweightDemo.java && java flyweight.FlyweightDemo
 */
public class FlyweightDemo {

    // Flyweight: immutable, shared intrinsic state.
    static final class TreeType {
        final String name, color, texture;
        TreeType(String name, String color, String texture) {
            this.name = name; this.color = color; this.texture = texture;
        }
        void draw(int x, int y) { // extrinsic state supplied by caller
            System.out.println("Draw " + name + "/" + color + " at (" + x + "," + y + ")");
        }
    }

    // Factory caches and reuses flyweights.
    static class TreeTypeFactory {
        private final Map<String, TreeType> cache = new HashMap<>();
        TreeType get(String name, String color, String texture) {
            return cache.computeIfAbsent(name + color + texture,
                    k -> new TreeType(name, color, texture));
        }
        int distinctTypes() { return cache.size(); }
    }

    public static void main(String[] args) {
        TreeTypeFactory factory = new TreeTypeFactory();
        // Plant 5 trees but only 2 distinct flyweights are ever created.
        int[][] positions = {{1,1},{2,5},{3,9},{7,7},{8,2}};
        String[][] specs = {
            {"Oak","Green","oak.png"}, {"Pine","DarkGreen","pine.png"},
            {"Oak","Green","oak.png"}, {"Pine","DarkGreen","pine.png"},
            {"Oak","Green","oak.png"}
        };
        for (int i = 0; i < positions.length; i++) {
            TreeType t = factory.get(specs[i][0], specs[i][1], specs[i][2]);
            t.draw(positions[i][0], positions[i][1]);
        }
        System.out.println("Trees planted: " + positions.length
                + ", distinct flyweights: " + factory.distinctTypes());
    }
}
