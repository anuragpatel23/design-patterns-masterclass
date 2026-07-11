package mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Mediator — a chat room coordinating users so they never reference each other.
 * Run: javac MediatorDemo.java && java mediator.MediatorDemo
 */
public class MediatorDemo {

    interface ChatMediator { void register(User u); void send(String from, String msg); }

    static class ChatRoom implements ChatMediator {
        private final List<User> users = new ArrayList<>();
        public void register(User u) { users.add(u); }
        public void send(String from, String msg) {
            for (User u : users) if (!u.name.equals(from)) u.receive(from, msg);
        }
    }

    static class User {
        final String name; private final ChatMediator room;
        User(String name, ChatMediator room) { this.name = name; this.room = room; room.register(this); }
        void send(String msg)  { System.out.println(name + " sends: " + msg); room.send(name, msg); }
        void receive(String from, String msg) { System.out.println("  " + name + " got from " + from + ": " + msg); }
    }

    public static void main(String[] args) {
        ChatMediator room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob   = new User("Bob", room);
        new User("Carol", room);
        alice.send("Hi all!");   // routed through the mediator to everyone else
        bob.send("Hey Alice");
    }
}
