package objectpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Object Pool — a bounded pool of "expensive" connections, borrow/return with timeout.
 * Run: javac ObjectPoolDemo.java && java objectpool.ObjectPoolDemo
 */
public class ObjectPoolDemo {

    static class ExpensiveConnection {
        private final int id;
        ExpensiveConnection(int id) {
            this.id = id;
            try { Thread.sleep(50); } catch (InterruptedException ignored) {} // simulate cost
        }
        String query(String sql) { return "conn#" + id + " ran: " + sql; }
        void reset() { /* clear per-borrow state here */ }
    }

    static class ConnectionPool implements AutoCloseable {
        private final BlockingQueue<ExpensiveConnection> idle;
        ConnectionPool(int size) {
            idle = new ArrayBlockingQueue<>(size);
            for (int i = 1; i <= size; i++) idle.add(new ExpensiveConnection(i));
        }
        ExpensiveConnection borrow() throws InterruptedException {
            ExpensiveConnection c = idle.poll(2, TimeUnit.SECONDS);
            if (c == null) throw new IllegalStateException("Pool exhausted (timeout)");
            return c;
        }
        void release(ExpensiveConnection c) { c.reset(); idle.offer(c); }
        public void close() { idle.clear(); }
    }

    public static void main(String[] args) throws InterruptedException {
        try (ConnectionPool pool = new ConnectionPool(2)) {
            ExpensiveConnection a = pool.borrow();
            ExpensiveConnection b = pool.borrow();
            System.out.println(a.query("SELECT 1"));
            System.out.println(b.query("SELECT 2"));
            pool.release(a);                       // return so the next borrow reuses it
            ExpensiveConnection c = pool.borrow(); // reuses 'a', no creation cost
            System.out.println(c.query("SELECT 3"));
            pool.release(b);
            pool.release(c);
        }
    }
}
