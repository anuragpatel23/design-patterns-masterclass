package unitofwork;

import java.util.*;

/**
 * Unit of Work — track new/dirty/removed entities and commit them together.
 * Run: javac UnitOfWorkDemo.java && java unitofwork.UnitOfWorkDemo
 */
public class UnitOfWorkDemo {

    static class Account {
        final String id; long balance;
        Account(String id, long balance) { this.id = id; this.balance = balance; }
        public String toString() { return id + "=" + balance; }
    }

    interface Db { void insert(Account a); void update(Account a); void delete(Account a); }

    static class InMemoryDb implements Db {
        final Map<String, Account> table = new LinkedHashMap<>();
        public void insert(Account a) { table.put(a.id, a); System.out.println("INSERT " + a); }
        public void update(Account a) { table.put(a.id, a); System.out.println("UPDATE " + a); }
        public void delete(Account a) { table.remove(a.id); System.out.println("DELETE " + a); }
    }

    static class UnitOfWork {
        private final Db db;
        private final List<Account> newObjs = new ArrayList<>();
        private final List<Account> dirtyObjs = new ArrayList<>();
        private final List<Account> removedObjs = new ArrayList<>();
        UnitOfWork(Db db) { this.db = db; }
        void registerNew(Account a) { newObjs.add(a); }
        void registerDirty(Account a) { dirtyObjs.add(a); }
        void registerRemoved(Account a) { removedObjs.add(a); }
        // One atomic flush of everything tracked.
        void commit() {
            System.out.println("-- commit --");
            newObjs.forEach(db::insert);
            dirtyObjs.forEach(db::update);
            removedObjs.forEach(db::delete);
            newObjs.clear(); dirtyObjs.clear(); removedObjs.clear();
        }
    }

    public static void main(String[] args) {
        Db db = new InMemoryDb();
        UnitOfWork uow = new UnitOfWork(db);

        Account a = new Account("A", 100);
        Account b = new Account("B", 50);
        uow.registerNew(a);
        uow.registerNew(b);
        // "Transfer" 30 from A to B — both changes tracked, committed together.
        a.balance -= 30; uow.registerDirty(a);
        b.balance += 30; uow.registerDirty(b);
        uow.commit();
    }
}
