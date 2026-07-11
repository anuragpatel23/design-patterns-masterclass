# 01. Singleton Pattern

**Category:** 🟢 Creational

> Ensure a class has exactly one instance and provide a global access point to it.

---

## Intent
Guarantee that a class has only one instance across the JVM and expose a single, well-known access point, so that shared state or a shared resource is coordinated through one object.

## Real-World Analogy
A country's **government**. However many citizens exist, there is exactly one government; every request for governance is routed through that single entity. You don't spin up a new government per request.

## When to Use
- Shared, expensive-to-create resources: connection pools, thread pools, caches, configuration, logging.
- When exactly one object must coordinate actions across the whole system.
- When a global access point is genuinely needed and the alternative (passing the object everywhere) is impractical.

## Structure
```
Client ---> Singleton
                 - static instance
                 - private constructor
                 + static getInstance()
```

## Participants
- **Singleton** — holds the single instance in a static field, hides the constructor, exposes `getInstance()`.

## Pros
- Controlled, lazy or eager single instantiation.
- Single point of access; saves memory for heavy objects.

## Cons / Trade-offs
- Introduces global state — hidden coupling, harder to reason about.
- Hard to unit-test in isolation; encourages hiding dependencies.
- Can become a bottleneck / concurrency hazard if it holds mutable state.

## Common Pitfalls (what interviewers probe)
- Non-thread-safe lazy init (the classic broken `if (instance==null)` race). Fix with the **enum**, **holder idiom**, or **double-checked locking + `volatile`**.
- Breaking singleton via reflection, serialization, or multiple classloaders — the enum singleton resists all three.
- Overuse: many 'singletons' are really just Spring beans (which are singletons by default) — don't hand-roll what the container gives you.

## Spring Boot / Framework Sightings
- Every `@Component`/`@Service`/`@Repository` bean is a **singleton scope** bean by default — one instance per container.
- `ApplicationContext` itself, `HikariDataSource`, `ObjectMapper` beans.

## Files in this folder
- **`SingletonDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`SingletonSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** What's the best way to implement a singleton in Java?
>
> **A:** An enum with a single constant — it's thread-safe by construction, and the JVM guarantees serialization and reflection can't create a second instance. For lazy initialization of a heavier object, the initialization-on-demand holder idiom is the cleanest.

> **Q:** Why is Singleton often called an anti-pattern?
>
> **A:** Because it smuggles in global mutable state and hidden dependencies, which hurts testability and coupling. In Spring you rarely need it — the container manages singleton lifecycle for you, and you inject the dependency explicitly instead of reaching for a static accessor.


## Related Patterns
Factory Method, Abstract Factory, Object Pool

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
