# 25. Object Pool Pattern

**Category:** 🟢 Creational

> Reuse a fixed set of expensive-to-create objects instead of allocating and discarding them repeatedly.

---

## Intent
Maintain a pool of reusable, expensive-to-create objects; clients borrow and return them, avoiding the cost of repeated creation and destruction.

## Real-World Analogy
A **library of books**: instead of everyone printing their own copy, readers borrow a book, use it, and return it for the next person. A limited number of copies serves many readers.

## When to Use
- Objects are costly to create (DB connections, threads, large buffers, sockets).
- You need to cap the number of concurrent instances of a scarce resource.
- Creation/teardown churn is a measurable performance problem.

## Structure
```
Client -> Pool.borrow() -> [reused object] -> Pool.release(object)
Pool: idle[] + inUse[] + max size
```

## Participants
- **Pool** — manages idle/in-use instances, hands out and reclaims them.
- **Reusable** — the pooled object; typically has a reset step on release.

## Pros
- Amortizes creation cost; smooths latency; bounds resource usage.

## Cons / Trade-offs
- Stale state leaking between borrowers if objects aren't reset.
- Pool exhaustion / contention needs careful sizing and timeouts.
- Adds complexity — don't pool cheap objects.

## Common Pitfalls (what interviewers probe)
- Not resetting borrowed objects -> data leaks across users.
- Leaking objects (forgetting to return them) -> starvation. Use try-with-resources / finally.
- Pooling plain Java objects that are cheap to GC — usually a net loss on modern JVMs.

## Spring Boot / Framework Sightings
- HikariCP connection pool (the canonical example).
- `ThreadPoolTaskExecutor` / `ThreadPoolExecutor`, Apache Commons-Pool for object pooling.

## Files in this folder
- **`ObjectPoolDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`ObjectPoolSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** When is object pooling a bad idea?
>
> **A:** For cheap, short-lived objects. Modern JVM allocation + generational GC is extremely fast, so pooling small POJOs usually adds contention and bugs (stale state) for no gain. Pool only genuinely expensive resources: connections, threads, large native buffers.


## Related Patterns
Singleton, Prototype, Flyweight

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
