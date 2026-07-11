# 16. Iterator Pattern

**Category:** 🟠 Behavioral

> Provide a way to access elements of a collection sequentially without exposing its underlying representation.

---

## Intent
Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

## Real-World Analogy
A **TV remote's channel-up button.** You move through channels one at a time without knowing how channels are stored internally. The remote is the iterator over the TV's channel collection.

## When to Use
- You want uniform traversal over different collection types.
- You need multiple simultaneous traversals or custom traversal orders.
- You want to hide a collection's internal structure from clients.

## Structure
```
Aggregate.iterator() -> Iterator (hasNext(), next())
```

## Participants
- **Iterator** — `hasNext()`, `next()`.
- **Aggregate** — creates an Iterator over itself.

## Pros
- Uniform traversal; supports multiple concurrent iterations; hides internals.

## Cons / Trade-offs
- For simple collections it's overhead — the JDK already provides `Iterator`/`Iterable`.

## Common Pitfalls (what interviewers probe)
- Concurrent modification during iteration (fail-fast exceptions).
- Reinventing the wheel — usually implement `Iterable<T>` and reuse the JDK.

## Spring Boot / Framework Sightings
- `Iterable`/`Iterator` everywhere; Spring Data `Page<T>`/`Slice<T>`; Spring Batch `ItemReader`.

## Files in this folder
- **`IteratorDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`IteratorSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Do you ever implement Iterator by hand in Java?
>
> **A:** Rarely from scratch — you implement Iterable<T> and let for-each and streams work. You hand-roll an Iterator when traversal order is non-trivial (tree traversal, lazy/streaming reads, paginated remote fetches) and you want to hide that complexity behind hasNext()/next().


## Related Patterns
Composite, Visitor, Factory Method

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
