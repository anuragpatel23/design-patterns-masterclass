# 05. Observer Pattern

**Category:** 🟠 Behavioral

> Define a one-to-many dependency so that when one object changes state, all its dependents are notified automatically.

---

## Intent
Define a one-to-many dependency between objects so that when the subject changes state, all registered observers are notified and updated automatically, with loose coupling between them.

## Real-World Analogy
A **newsletter subscription.** The publisher doesn't know who its readers are individually; when a new issue drops, every subscriber gets notified. Subscribers come and go without the publisher changing.

## When to Use
- A change to one object requires changing an open-ended set of others.
- You want event-driven, decoupled notification (UI events, domain events).
- The subject shouldn't know the concrete types of its listeners.

## Structure
```
Subject (register/notify) --> Observer.update()
   ^                                  ^
ConcreteSubject                  ConcreteObserver(s)
```

## Participants
- **Subject** — maintains observers, notifies them of changes.
- **Observer** — defines the update interface.
- **ConcreteObserver** — reacts to notifications.

## Pros
- Loose coupling between subject and observers; Open/Closed for new observers.
- Supports broadcast/event-driven designs.

## Cons / Trade-offs
- Notification order is unspecified; cascades can be hard to trace.
- Risk of memory leaks if observers aren't unregistered (lapsed listener).

## Common Pitfalls (what interviewers probe)
- Synchronous observers blocking the subject — use async where appropriate.
- Exceptions in one observer breaking the whole notification loop.

## Spring Boot / Framework Sightings
- `ApplicationEventPublisher` + `@EventListener` (+ `@Async` for non-blocking).
- Scales out to Kafka/RabbitMQ topics for distributed observers.

## Files in this folder
- **`ObserverDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`ObserverSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** How does Spring implement Observer?
>
> **A:** Through the application event mechanism: publish an event with ApplicationEventPublisher, and any bean method annotated @EventListener receives it. Adding @Async makes the listener non-blocking. It's the in-process version; for cross-service it graduates to a message broker like Kafka.

> **Q:** What's the classic Observer memory leak?
>
> **A:** The lapsed-listener problem: an observer registers but never unregisters, so the subject keeps a strong reference and the observer never gets garbage-collected. Fixes: explicit removal, weak references, or a framework that manages lifecycle.


## Related Patterns
Mediator, Command, Chain of Responsibility

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
