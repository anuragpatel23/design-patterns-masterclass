# 09. Adapter Pattern

**Category:** 🔵 Structural

> Convert the interface of a class into another interface clients expect.

---

## Intent
Convert the interface of a class into another interface that clients expect, letting classes work together that otherwise couldn't due to incompatible interfaces.

## Real-World Analogy
A **power plug travel adapter.** Your laptop's US plug can't fit a European socket, so an adapter sits between them — translating one interface into the other without changing either device.

## When to Use
- Integrate a third-party/legacy class whose interface you can't change.
- Unify several incompatible APIs behind one interface your code expects.
- Wrap an external system (payment gateway, SMS provider) behind a domain interface.

## Structure
```
Client -> Target (expected interface)
                 ^
              Adapter --wraps--> Adaptee (incompatible)
```

## Participants
- **Target** — the interface the client expects.
- **Adaptee** — the existing, incompatible class.
- **Adapter** — implements Target, translates calls to the Adaptee.

## Pros
- Reuse existing/third-party code without modifying it.
- Isolates external APIs behind your own domain interface (single change point).

## Cons / Trade-offs
- Extra layer; too many adapters can obscure the real flow.

## Common Pitfalls (what interviewers probe)
- Confusing Adapter (change interface) with Facade (simplify a subsystem) and Decorator (add behavior, same interface).
- Leaking Adaptee types through the Target interface, coupling clients to the thing you tried to hide.

## Spring Boot / Framework Sightings
- `@FeignClient` adapts a REST API to a Java interface.
- `HandlerAdapter` in Spring MVC; `WebMvcConfigurer`; wrapper services around vendor SDKs.

## Files in this folder
- **`AdapterDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`AdapterSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Adapter vs Facade?
>
> **A:** Adapter changes an interface to match what a client expects, usually wrapping a single class. Facade doesn't change interfaces to match anything — it invents a new, simpler interface over a whole complex subsystem. Adapter is about compatibility; Facade is about simplification.


## Related Patterns
Facade, Decorator, Proxy

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
