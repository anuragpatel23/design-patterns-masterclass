# 18. Prototype Pattern

**Category:** 🟢 Creational

> Create new objects by cloning an existing instance rather than constructing from scratch.

---

## Intent
Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype — useful when construction is expensive or the exact class is decided at runtime.

## Real-World Analogy
A **cell dividing (mitosis)**: instead of building a new cell atom by atom, an existing cell copies itself. Or a document 'template' you duplicate and tweak rather than authoring from a blank page each time.

## When to Use
- Object creation is expensive (heavy initialization, DB/network lookups) and you need many similar instances.
- You want to avoid a parallel hierarchy of factory classes.
- You need per-request mutable copies of a preconfigured object.

## Structure
```
Prototype (clone())  <--  ConcretePrototype
Client -> prototype.clone() -> new copy
```

## Participants
- **Prototype** — declares a `clone()` operation.
- **ConcretePrototype** — implements copying (deep or shallow).

## Pros
- Cheaper than re-running expensive construction.
- Adds/removes products at runtime by registering prototypes.

## Cons / Trade-offs
- Deep-copying object graphs with cycles is tricky.
- Java's `Cloneable`/`clone()` is famously awkward — prefer copy constructors or serialization-based copies.

## Common Pitfalls (what interviewers probe)
- Shallow copy sharing mutable inner state between clones (aliasing bugs).
- Relying on `Object.clone()` — most experts prefer a copy constructor or a `copy()` method.

## Spring Boot / Framework Sightings
- `@Scope("prototype")` beans — a fresh instance on every injection/lookup.
- Copying a preconfigured template object (e.g., a base `RestClient`/`WebClient` builder mutated per call).

## Files in this folder
- **`PrototypeDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`PrototypeSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Spring's prototype scope vs the GoF Prototype pattern — same thing?
>
> **A:** Related but distinct. Spring's prototype scope means 'new bean instance per request from the container' — the container constructs it. The GoF pattern means 'produce a new object by copying an existing instance.' Spring prototype scope solves lifecycle; GoF Prototype solves cheap duplication.


## Related Patterns
Factory Method, Abstract Factory, Memento

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
