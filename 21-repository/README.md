# 21. Repository Pattern

**Category:** 🟣 Enterprise / Architectural

> Mediate between the domain and data-mapping layers using a collection-like interface for accessing domain objects.

---

## Intent
Provide a collection-like abstraction over persistence so the domain works with objects without knowing about the database, ORM, or query mechanics, keeping domain logic testable and infrastructure-agnostic.

## Real-World Analogy
A **librarian.** You ask for a book by criteria ('the 2019 tax law commentary'); the librarian finds it — you don't care whether it's on a shelf, in storage, or on microfiche. The repository hides where and how the data actually lives.

## When to Use
- You want domain/business logic decoupled from persistence technology.
- You want to unit-test domain logic without a real database.
- You want a single place to centralize query logic per aggregate.

## Structure
```
Domain -> Repository (interface: find/save/delete)
                   ^
            JpaRepositoryImpl (infrastructure)
```

## Participants
- **Repository interface** — domain-facing, collection-like (`findById`, `save`, `findByX`).
- **Repository implementation** — infrastructure (JPA/JDBC/Mongo).

## Pros
- Decouples domain from persistence; swappable data store; highly testable.
- Centralizes query logic; supports Hexagonal/Ports-and-Adapters architecture.

## Cons / Trade-offs
- Extra abstraction; risk of anemic repositories that just proxy the ORM.
- Leaky abstractions if ORM types bleed into the domain interface.

## Common Pitfalls (what interviewers probe)
- Exposing `EntityManager`/`Criteria`/`Pageable` in the DOMAIN interface, defeating the decoupling.
- Generic 'repository of everything' — prefer one repository per aggregate root.

## Spring Boot / Framework Sightings
- `JpaRepository`/`CrudRepository` — Spring Data generates the implementation from the interface.
- Define a domain-facing port and adapt Spring Data behind it for clean architecture.

## Files in this folder
- **`RepositoryDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`RepositorySpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Is Spring Data's JpaRepository the Repository pattern?
>
> **A:** It's a powerful implementation of it, but purists note it leaks persistence concepts (Pageable, ORM types) into what should be a domain interface. In clean/hexagonal architecture you often define your own domain-facing repository port with domain language, then implement it using a Spring Data repository underneath — getting both testability and Spring's productivity.


## Related Patterns
Unit of Work, Specification, Facade

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
