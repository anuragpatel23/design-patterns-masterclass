# 07. Decorator Pattern

**Category:** 🔵 Structural

> Attach additional responsibilities to an object dynamically by wrapping it.

---

## Intent
Attach additional responsibilities to an object dynamically. Decorators are a flexible alternative to subclassing for extending behavior.

## Real-World Analogy
**Coffee at Starbucks.** Start with espresso, then wrap it with milk, then whipped cream, then caramel. Each wrapper adds cost and description while the base drink is untouched; the final drink carries all additions.

## When to Use
- Add behavior to individual objects without affecting others of the same class.
- Subclassing would cause a combinatorial explosion of classes (milk×cream×caramel×...).
- You want to compose behaviors at runtime and in any order.

## Structure
```
Component (interface)
   ^                 ^
ConcreteComponent   Decorator (wraps a Component)
                        ^
              ConcreteDecoratorA / B (add behavior, delegate)
```

## Participants
- **Component** — common interface.
- **ConcreteComponent** — the core object being decorated.
- **Decorator** — holds a Component and forwards; concrete decorators add behavior.

## Pros
- Composition over inheritance; mix behaviors at runtime.
- Follows Single Responsibility — each decorator does one thing.

## Cons / Trade-offs
- Many small wrapper objects; debugging a deep stack is harder.
- Order of decoration can matter and be surprising.

## Common Pitfalls (what interviewers probe)
- Decorator must implement the same interface as the component — otherwise clients can't treat them uniformly.
- Confusing Decorator (adds behavior, same interface) with Proxy (controls access) and Adapter (changes interface).

## Spring Boot / Framework Sightings
- Spring AOP, `@Transactional`, `@Cacheable`, `@Async` — all decorate your bean via a proxy.
- `HttpServletRequestWrapper`, `BufferedReader` wrapping `Reader` (java.io is decorator-heavy).

## Files in this folder
- **`DecoratorDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`DecoratorSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Decorator vs Proxy vs Adapter?
>
> **A:** All wrap an object, but intent differs. Decorator adds responsibilities while keeping the same interface. Proxy controls access (lazy load, security, remoting) with the same interface. Adapter changes the interface so an incompatible class fits. Same mechanism, three different goals.

> **Q:** Where does Spring use Decorator?
>
> **A:** Everywhere via AOP. @Transactional, @Cacheable, @Async, and @Retryable all create a proxy that decorates your method call with cross-cutting behavior before delegating to the real bean.


## Related Patterns
Proxy, Adapter, Composite

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
