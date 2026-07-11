# 17. Mediator Pattern

**Category:** 🟠 Behavioral

> Centralize complex communications between related objects into a mediator, reducing direct coupling.

---

## Intent
Define an object that encapsulates how a set of objects interact, promoting loose coupling by keeping objects from referring to each other explicitly.

## Real-World Analogy
An **air-traffic control tower.** Planes don't negotiate directly with each other; they all talk to the tower, which coordinates takeoffs and landings. Without it, N planes would need N² conversations.

## When to Use
- A set of objects communicate in complex, tangled ways (many-to-many).
- Reusing an object is hard because it refers to and talks to many others.
- You want to centralize control logic that's smeared across peers.

## Structure
```
Colleague <-> Mediator <-> Colleague
All colleagues talk only to the Mediator.
```

## Participants
- **Mediator** — defines the coordination interface.
- **ConcreteMediator** — coordinates colleagues.
- **Colleague** — talks to the mediator, not to peers.

## Pros
- Reduces many-to-many coupling to many-to-one; centralizes interaction logic.

## Cons / Trade-offs
- The mediator can become a complex 'god object' if overloaded.

## Common Pitfalls (what interviewers probe)
- Confusing Mediator (colleagues know the mediator) with Observer (subject broadcasts blindly).
- Letting the mediator absorb business logic that belongs elsewhere.

## Spring Boot / Framework Sightings
- `ApplicationEventPublisher` as an in-process mediator; a message broker (Kafka) as a distributed mediator.
- Spring MVC `DispatcherServlet` mediates between requests and handlers.

## Files in this folder
- **`MediatorDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`MediatorSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Mediator vs Observer?
>
> **A:** Both decouple, but differently. In Observer, the subject broadcasts to observers it doesn't know individually — one-to-many, unidirectional. In Mediator, colleagues explicitly route interactions through a central hub that coordinates them — many-to-many collapsed into many-to-one. DispatcherServlet is a mediator; ApplicationEvent listeners are observers.


## Related Patterns
Observer, Facade, Command

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
