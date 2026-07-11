# 06. Strategy Pattern

**Category:** 🟠 Behavioral

> Define a family of interchangeable algorithms and select one at runtime.

---

## Intent
Define a family of algorithms, encapsulate each, and make them interchangeable so the algorithm can vary independently from the clients that use it.

## Real-World Analogy
**Google Maps travel modes.** Same destination, but Car / Bicycle / Walking / Transit each use a completely different route-calculation algorithm. You pick a mode; the app delegates to that strategy.

## When to Use
- You have multiple ways to do one thing and want to switch at runtime.
- You want to eliminate sprawling if/else or switch chains over a 'type'.
- Algorithms should be independently testable and extendable.

## Structure
```
Context -> Strategy (interface)
                  ^
     ConcreteStrategyA / B / C
```

## Participants
- **Strategy** — common interface for all algorithms.
- **ConcreteStrategy** — a specific algorithm.
- **Context** — holds a Strategy and delegates to it.

## Pros
- Open/Closed — add strategies without touching the context.
- Replaces conditionals with polymorphism; isolates and tests each algorithm.

## Cons / Trade-offs
- More classes; clients must know which strategy to pick.

## Common Pitfalls (what interviewers probe)
- Strategies with wildly different parameter needs, forcing a leaky common interface.
- Confusing Strategy (interchangeable how) with State (transitions between behaviors).

## Spring Boot / Framework Sightings
- `Map<String, Strategy>` injection (idiomatic selector).
- `PasswordEncoder`, `TaskExecutor`, `PlatformTransactionManager` are pluggable strategies.

## Files in this folder
- **`StrategyDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`StrategySpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Strategy vs State — they look identical.
>
> **A:** Structurally similar, semantically different. Strategy is chosen by the client and doesn't change itself — you pick an algorithm. State is chosen by the object based on its internal condition, and states transition to one another. Strategy = 'how do I compute this'; State = 'what am I right now, and what can I become'.


## Related Patterns
State, Template Method, Factory Method

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
