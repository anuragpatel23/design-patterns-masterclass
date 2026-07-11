# 11. Command Pattern

**Category:** 🟠 Behavioral

> Encapsulate a request as an object, enabling queuing, logging, and undo.

---

## Intent
Encapsulate a request as an object, letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

## Real-World Analogy
A **restaurant order ticket.** The waiter writes your request on a ticket (command object). It can be queued, passed to the kitchen, logged, and even cancelled — all without the waiter knowing how the dish is cooked.

## When to Use
- You need to queue, schedule, log, or undo operations.
- You want to decouple the invoker of an operation from its receiver.
- You want to represent actions as first-class objects (macro, transactions).

## Structure
```
Invoker -> Command.execute() -> Receiver.action()
ConcreteCommand holds Receiver + params (+ undo()).
```

## Participants
- **Command** — declares `execute()` (and optionally `undo()`).
- **ConcreteCommand** — binds a Receiver + action.
- **Invoker** — triggers commands; **Receiver** — does the work.

## Pros
- Decouples invoker from receiver; commands are queueable/loggable/undoable.
- Easy to add new commands (Open/Closed); supports macros and transactions.

## Cons / Trade-offs
- Many small command classes.

## Common Pitfalls (what interviewers probe)
- Undo that doesn't restore the exact prior state (store enough to reverse).
- Leaking receiver internals into the invoker.

## Spring Boot / Framework Sightings
- Spring Batch `Step`/`Tasklet`, `TaskExecutor` submitting `Runnable`s.
- Message handlers / CQRS command handlers.

## Files in this folder
- **`CommandDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`CommandSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** How does Command enable undo?
>
> **A:** Each ConcreteCommand captures enough state to reverse itself and exposes undo(). The invoker keeps a history stack; undo pops the last command and calls its undo(). Combined with Memento, you can undo complex state changes cleanly.


## Related Patterns
Memento, Chain of Responsibility, Strategy

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
