# 20. Visitor Pattern

**Category:** 🟠 Behavioral

> Add new operations to an object structure without modifying the objects themselves.

---

## Intent
Represent an operation to be performed on the elements of an object structure, letting you define a new operation without changing the classes of the elements it operates on (double dispatch).

## Real-World Analogy
A **tax auditor visiting different businesses.** Each business (element) accepts the auditor (visitor), and the auditor applies the right procedure per business type. New audit types are added by writing a new auditor — the businesses don't change.

## When to Use
- A stable object structure needs many unrelated operations added over time.
- You want to keep related operations together instead of scattering them across element classes.
- You're building AST processors, report generators, or exporters.

## Structure
```
element.accept(visitor) -> visitor.visit(ConcreteElement)
One Visitor per operation; visit() overloaded per element type.
```

## Participants
- **Visitor** — a `visit()` method per concrete element type.
- **Element** — `accept(Visitor)` calling back the right `visit()`.
- **ConcreteElement / ConcreteVisitor**.

## Pros
- Add new operations without touching element classes.
- Groups related behavior in one visitor.

## Cons / Trade-offs
- Adding a new element type forces changing every visitor (the dual trade-off).
- Breaks encapsulation if visitors need element internals.

## Common Pitfalls (what interviewers probe)
- Using it when element types change more often than operations — then it's the wrong trade-off.
- Verbosity; consider sealed types + pattern matching (Java 21) as a modern alternative.

## Spring Boot / Framework Sightings
- AST/bytecode visitors (ASM), JPA Criteria tree processing, report/export generators.

## Files in this folder
- **`VisitorDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`VisitorSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** What is 'double dispatch' and why does Visitor need it?
>
> **A:** Double dispatch means the executed method depends on the runtime types of two objects — the element and the visitor. Java only dispatches on the receiver, so Visitor simulates it: element.accept(visitor) dispatches on the element, then calls visitor.visit(this), which dispatches on the visitor and the element's static type. That two-step is what lets the right operation run for the right element.

> **Q:** Modern alternative to Visitor in Java?
>
> **A:** Java 21 sealed interfaces plus switch pattern matching. You get exhaustive, compiler-checked handling of a closed set of types without the accept/visit boilerplate — great when the type hierarchy is sealed and stable.


## Related Patterns
Composite, Iterator, Interpreter

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
