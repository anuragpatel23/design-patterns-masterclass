# 04. Builder Pattern

**Category:** 🟢 Creational

> Separate the construction of a complex object from its representation for step-by-step, validated, immutable creation.

---

## Intent
Separate construction of a complex object from its representation so the same process can build different representations — especially useful for immutable objects with many optional fields.

## Real-World Analogy
Ordering a **custom burger**: 'brioche bun, double patty, no onions, extra cheese, BBQ sauce.' The kitchen (builder) assembles it step by step. You get exactly what you specified without a separate class for every combination.

## When to Use
- Objects with many optional parameters (avoid telescoping constructors).
- Immutable objects that still need flexible, readable construction.
- Construction requires validation or ordering of steps.

## Structure
```
Client -> Builder (fluent setters) -> build() -> Product (immutable)
```

## Participants
- **Product** — the complex, usually immutable object.
- **Builder** — accumulates parts via fluent methods and validates in `build()`.

## Pros
- Readable, self-documenting construction; no positional-arg confusion.
- Validation in one place; supports immutability.

## Cons / Trade-offs
- More boilerplate (mitigated by Lombok `@Builder`).
- Overkill for objects with 2–3 fields.

## Common Pitfalls (what interviewers probe)
- Forgetting to validate in `build()`.
- Mutable Product defeating the purpose — make fields `final`.
- With Lombok, forgetting `@Builder.Default` so defaults are silently lost.

## Spring Boot / Framework Sightings
- Lombok `@Builder` / `@Value`.
- `UriComponentsBuilder`, `RestClient`/`WebClient` fluent builders, `Stream`/`Collectors` builders.

## Files in this folder
- **`BuilderDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`BuilderSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Why Builder over a constructor with many parameters?
>
> **A:** Telescoping constructors are unreadable and error-prone — two `boolean`s next to each other are a bug waiting to happen. A builder names each field at the call site, allows optional fields with defaults, and centralizes validation in build(), all while keeping the object immutable.


## Related Patterns
Factory Method, Abstract Factory, Prototype

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
