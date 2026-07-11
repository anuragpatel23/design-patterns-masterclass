package memento;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Memento — an editor whose state is snapshotted opaquely; the caretaker manages
 * an undo history without seeing the memento's internals.
 * Run: javac MementoDemo.java && java memento.MementoDemo
 */
public class MementoDemo {

    // Opaque memento: fields are private; only Originator constructs/reads it.
    static final class Memento {
        private final String state;
        private Memento(String state) { this.state = state; }
        private String state() { return state; }
    }

    // Originator
    static class Editor {
        private String content = "";
        void type(String s) { content += s; }
        String content() { return content; }
        Memento save() { return new Memento(content); }
        void restore(Memento m) { this.content = m.state(); }
    }

    // Caretaker: keeps history, never inspects a memento's guts.
    static class History {
        private final Deque<Memento> stack = new ArrayDeque<>();
        void push(Memento m) { stack.push(m); }
        Memento pop() { return stack.pop(); }
        boolean isEmpty() { return stack.isEmpty(); }
    }

    public static void main(String[] args) {
        Editor editor = new Editor();
        History history = new History();

        editor.type("Hello");
        history.push(editor.save());
        editor.type(", World");
        System.out.println("Before undo: " + editor.content());

        if (!history.isEmpty()) editor.restore(history.pop());
        System.out.println("After undo:  " + editor.content());
    }
}
