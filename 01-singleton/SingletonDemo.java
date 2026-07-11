package singleton;

/**
 * Singleton pattern — three correct implementations plus a demonstration.
 *
 *  1) Enum singleton            : simplest, safest (reflection & serialization proof).
 *  2) Holder idiom (lazy)       : thread-safe lazy init without synchronization cost.
 *  3) Double-checked locking    : classic lazy, needs `volatile`.
 *
 * Run: javac SingletonDemo.java && java singleton.SingletonDemo
 */
public class SingletonDemo {

    // ---- 1) ENUM SINGLETON (preferred) --------------------------------------
    public enum Government {
        INSTANCE;
        private int lawsPassed = 0;
        public void passLaw(String law) {
            lawsPassed++;
            System.out.println("[Government] Passed law: " + law + " (total=" + lawsPassed + ")");
        }
    }

    // ---- 2) INITIALIZATION-ON-DEMAND HOLDER (lazy, thread-safe) --------------
    static final class ConfigManager {
        private final long createdAt = System.nanoTime();
        private ConfigManager() { System.out.println("[Config] created lazily"); }
        // Holder is loaded only when getInstance() is first called.
        private static class Holder { static final ConfigManager INSTANCE = new ConfigManager(); }
        public static ConfigManager getInstance() { return Holder.INSTANCE; }
        public long createdAt() { return createdAt; }
    }

    // ---- 3) DOUBLE-CHECKED LOCKING (classic) --------------------------------
    static final class ConnectionPool {
        private static volatile ConnectionPool instance;   // volatile is essential
        private ConnectionPool() { System.out.println("[Pool] created"); }
        public static ConnectionPool getInstance() {
            if (instance == null) {
                synchronized (ConnectionPool.class) {
                    if (instance == null) instance = new ConnectionPool();
                }
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        // 1) Enum
        Government.INSTANCE.passLaw("Clean Air Act");
        Government.INSTANCE.passLaw("Data Protection Act");
        System.out.println("Same government? " + (Government.INSTANCE == Government.INSTANCE));

        // 2) Holder idiom
        ConfigManager a = ConfigManager.getInstance();
        ConfigManager b = ConfigManager.getInstance();
        System.out.println("Same config instance? " + (a == b));

        // 3) Double-checked locking
        ConnectionPool p1 = ConnectionPool.getInstance();
        ConnectionPool p2 = ConnectionPool.getInstance();
        System.out.println("Same pool instance? " + (p1 == p2));
    }
}
