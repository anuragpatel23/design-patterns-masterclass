# 22. Unit of Work Pattern

**Category:** 🟣 Enterprise / Architectural

> Track changes made during a business transaction and commit them all atomically.

---

## Intent
Maintain a list of objects affected by a business transaction and coordinate writing out changes and resolving concurrency, so multiple changes commit atomically as one unit.

## Real-World Analogy
A **shopping cart at checkout.** You add, remove, and change quantities freely; nothing is billed until you check out. At checkout everything is committed together — or, if payment fails, nothing is.

## When to Use
- Multiple changes across entities must succeed or fail together (atomicity).
- You want to batch writes and reduce round-trips.
- You need consistent flush/commit boundaries around a business operation.

## Structure
```
UnitOfWork: registerNew/Dirty/Removed(...) then commit() -> one atomic flush
```

## Participants
- **Unit of Work** — tracks new/dirty/removed objects; `commit()` flushes atomically.
- **Repositories/Entities** — register their changes with the UoW.

## Pros
- Atomic multi-entity commits; fewer DB round-trips; consistent transaction boundaries.

## Cons / Trade-offs
- Change-tracking complexity if hand-rolled.
- Long-lived units of work can hold locks / grow memory.

## Common Pitfalls (what interviewers probe)
- Assuming `@Transactional` on a private/self-invoked method works (it won't — proxy).
- Doing external I/O (emails, HTTP) inside the transaction and rolling back the DB but not the side effect.

## Spring Boot / Framework Sightings
- `@Transactional` + the JPA persistence context IS a Unit of Work — dirty checking + a single flush at commit.

## Files in this folder
- **`UnitOfWorkDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`UnitOfWorkSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Where is Unit of Work in Spring?
>
> **A:** The JPA persistence context under @Transactional. Within a transaction, managed entities are change-tracked (dirty checking); at commit, Hibernate computes the minimal set of writes and flushes them as one atomic unit. You don't call save() for every field change — the persistence context is the Unit of Work.


## Related Patterns
Repository, Command, Memento

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
