# 08. Proxy Pattern

**Category:** 🔵 Structural

> Provide a surrogate or placeholder for another object to control access to it.

---

## Intent
Provide a stand-in for another object to control access — for lazy loading (virtual), access control (protection), remote access (remote), or caching.

## Real-World Analogy
A **credit card** is a proxy for your bank account. You hand over the card, not your passbook. It controls access (PIN, limits), logs activity, and delegates the real money movement to the bank.

## When to Use
- Virtual proxy: defer creating an expensive object until first use.
- Protection proxy: check permissions before delegating.
- Remote proxy: represent an object living in another process/machine.
- Caching/logging proxy: add cross-cutting behavior around calls.

## Structure
```
Client -> Subject (interface)
              ^         ^
        RealSubject   Proxy --controls--> RealSubject
```

## Participants
- **Subject** — common interface for RealSubject and Proxy.
- **RealSubject** — the actual object.
- **Proxy** — same interface, controls access to RealSubject.

## Pros
- Adds control (security, lazy init, caching, remoting) transparently.
- Client is unaware it's talking to a proxy.

## Cons / Trade-offs
- Extra indirection/latency.
- Can hide behavior, complicating debugging.

## Common Pitfalls (what interviewers probe)
- Self-invocation in Spring: calling another @Transactional method on `this` bypasses the proxy, so the annotation is ignored.
- Confusing Proxy (same interface, controls access) with Decorator (same interface, adds behavior).

## Spring Boot / Framework Sightings
- `@Transactional`, `@Async`, `@Cacheable` create JDK dynamic proxies or CGLIB proxies.
- `@FeignClient` is a remote proxy; Spring Data repositories are proxies over your interface.

## Files in this folder
- **`ProxyDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`ProxySpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Why does @Transactional silently not work when I call the method from within the same class?
>
> **A:** Spring wraps the bean in a proxy, and the annotation is applied by the proxy. An internal `this.method()` call goes straight to the target and skips the proxy, so the advice never runs. Fixes: call through the injected bean reference, self-inject, or refactor into a separate bean.


## Related Patterns
Decorator, Adapter, Facade

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
