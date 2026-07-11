# 12. Chain of Responsibility Pattern

**Category:** 🟠 Behavioral

> Pass a request along a chain of handlers until one handles it.

---

## Intent
Avoid coupling the sender of a request to its receiver by giving multiple objects a chance to handle it; chain the receivers and pass the request along until one handles it.

## Real-World Analogy
**Customer support escalation.** Your ticket goes to L1; if they can't solve it, it escalates to L2, then to engineering. Each level either handles it or passes it on. You (the sender) don't need to know who ultimately handles it.

## When to Use
- More than one object may handle a request and the handler isn't known a priori.
- You want to issue a request to one of several handlers without hard-coding the receiver.
- The set/order of handlers should be configurable (pipelines, filters, middleware).

## Structure
```
Handler (setNext, handle) -> Handler -> Handler -> ...
Each either handles or delegates to next.
```

## Participants
- **Handler** — defines `handle()` and holds a reference to the next handler.
- **ConcreteHandler** — handles what it can, else forwards.

## Pros
- Decouples sender and receiver; flexible, reorderable pipelines (Open/Closed).

## Cons / Trade-offs
- No guarantee a request is handled (may fall off the end).
- Can be hard to observe/debug long chains.

## Common Pitfalls (what interviewers probe)
- Forgetting a terminal/default handler, so unhandled requests silently vanish.
- Building cycles in the chain.

## Spring Boot / Framework Sightings
- Servlet `FilterChain`, Spring Security filter chain, Spring MVC `HandlerInterceptor` chain.
- Any middleware/interceptor pipeline.

## Files in this folder
- **`ChainOfResponsibilityDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`ChainOfResponsibilitySpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Give a real framework example of Chain of Responsibility.
>
> **A:** Spring Security's filter chain. A request passes through authentication, authorization, CSRF, etc.; each filter either handles/short-circuits or calls chain.doFilter() to pass control down the line. The Servlet FilterChain itself is the pattern.


## Related Patterns
Command, Decorator, Composite

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
