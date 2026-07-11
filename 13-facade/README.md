# 13. Facade Pattern

**Category:** 🔵 Structural

> Provide a unified, simplified interface to a set of interfaces in a subsystem.

---

## Intent
Provide a single simplified entry point to a complex subsystem, reducing coupling between clients and the subsystem's many moving parts.

## Real-World Analogy
A **restaurant waiter.** You don't talk to the chef, the bartender, and the dishwasher separately — you talk to the waiter, who coordinates the whole subsystem behind one simple interface: 'I'd like the steak.'

## When to Use
- A subsystem is complex and clients only need a common subset of its capabilities.
- You want to decouple clients from subsystem internals so they can evolve independently.
- You want a clear, high-level API layer (e.g., a service orchestrating repositories + external calls).

## Structure
```
Client -> Facade -> { SubsystemA, SubsystemB, SubsystemC }
```

## Participants
- **Facade** — the simple interface that delegates to subsystem classes.
- **Subsystem classes** — do the real work; unaware of the facade.

## Pros
- Simplifies client code; reduces coupling to internals.
- Provides a stable API while internals change.

## Cons / Trade-offs
- Risk of becoming a 'god object' if it accumulates too much.
- Can hide capabilities clients occasionally need.

## Common Pitfalls (what interviewers probe)
- Letting the facade grow into a dumping ground of unrelated methods.
- Facade doesn't forbid direct subsystem access — it just offers a convenient path.

## Spring Boot / Framework Sightings
- Your `@Service` layer is usually a facade over repositories + external clients.
- `JdbcTemplate`, `RestTemplate`/`RestClient` are facades over verbose lower-level APIs.

## Files in this folder
- **`FacadeDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`FacadeSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Isn't a Spring @Service just a Facade?
>
> **A:** Often, yes. A well-designed application service is a facade that orchestrates repositories, mappers, and external clients behind one intention-revealing method like placeOrder(). It keeps controllers thin and hides persistence/integration details from the web layer.


## Related Patterns
Adapter, Mediator, Proxy

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
