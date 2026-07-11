# 10. Template Method Pattern

**Category:** 🟠 Behavioral

> Define the skeleton of an algorithm in a base class, deferring specific steps to subclasses.

---

## Intent
Define the skeleton of an algorithm in an operation, deferring some steps to subclasses, so subclasses can redefine certain steps without changing the algorithm's overall structure.

## Real-World Analogy
A **recipe with a fixed sequence** — prepare, cook, plate — where the 'cook' step differs per dish. The order never changes; only the variable steps are filled in by each specific recipe.

## When to Use
- Several algorithms share the same structure but differ in specific steps.
- You want to enforce an invariant sequence while allowing controlled variation.
- You want to pull duplicated skeleton code into one place.

## Structure
```
AbstractClass
  + templateMethod() {step1(); step2(); hook();}
  # abstract step2()
      ^
 ConcreteClass overrides step2()
```

## Participants
- **AbstractClass** — defines `templateMethod()` and abstract/hook steps.
- **ConcreteClass** — implements the variable steps.

## Pros
- Removes duplication; enforces algorithm structure.
- Fine-grained extension points via hooks.

## Cons / Trade-offs
- Inheritance-based — less flexible than composition (Strategy).
- Can violate LSP if subclasses subvert the skeleton.

## Common Pitfalls (what interviewers probe)
- Making the template method non-final so subclasses can accidentally override the whole algorithm.
- Deep hierarchies that are hard to follow; prefer Strategy when steps vary independently.

## Spring Boot / Framework Sightings
- `JdbcTemplate`, `RestTemplate`, `TransactionTemplate` — you supply the varying callback; they own the skeleton.
- `AbstractController`, Spring Batch step scaffolding.

## Files in this folder
- **`TemplateMethodDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`TemplateMethodSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Template Method vs Strategy?
>
> **A:** Template Method varies steps via inheritance and keeps the algorithm skeleton in a base class — 'don't call us, we'll call you'. Strategy varies the whole algorithm via composition and delegation. Template Method fixes the sequence and lets you fill blanks; Strategy swaps the entire behavior object.


## Related Patterns
Strategy, Factory Method, Command

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
