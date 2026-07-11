package command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Command — a text editor with executable, undoable commands and a history stack.
 * Run: javac CommandDemo.java && java command.CommandDemo
 */
public class CommandDemo {

    // Receiver
    static class Document {
        private final StringBuilder text = new StringBuilder();
        void append(String s) { text.append(s); }
        void deleteLast(int n) { text.delete(text.length() - n, text.length()); }
        public String toString() { return text.toString(); }
    }

    interface Command { void execute(); void undo(); }

    static class AppendCommand implements Command {
        private final Document doc; private final String text;
        AppendCommand(Document doc, String text) { this.doc = doc; this.text = text; }
        public void execute() { doc.append(text); }
        public void undo()    { doc.deleteLast(text.length()); }
    }

    // Invoker with undo history
    static class Editor {
        private final Deque<Command> history = new ArrayDeque<>();
        void run(Command c) { c.execute(); history.push(c); }
        void undo() { if (!history.isEmpty()) history.pop().undo(); }
    }

    public static void main(String[] args) {
        Document doc = new Document();
        Editor editor = new Editor();
        editor.run(new AppendCommand(doc, "Hello"));
        editor.run(new AppendCommand(doc, ", World"));
        System.out.println("After edits: " + doc);
        editor.undo();
        System.out.println("After undo:  " + doc);
    }
}
