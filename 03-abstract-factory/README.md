# 03. Abstract Factory Pattern

**Category:** 🟢 Creational

> Provide an interface for creating families of related objects without specifying their concrete classes.

---

## Intent
Provide an interface for creating families of related or dependent objects without naming their concrete classes, so a whole product family can be swapped by swapping the factory.

## Real-World Analogy
**IKEA furniture ranges.** The 'Scandinavian' range gives you a matching sofa, table, and lamp; the 'Modern' range gives its own matched set. You pick a range (factory) and get a consistent family — you never accidentally mix a Scandinavian sofa with a Modern lamp.

## When to Use
- The system must be independent of how its products are created and composed.
- You must guarantee that related objects are used together (UI toolkit theme: button + checkbox must match).
- You want to switch an entire product family at once (e.g., a whole persistence stack).

## Structure
```
AbstractFactory                ProductA   ProductB
 + createA(): A                  ^          ^
 + createB(): B                  |          |
     ^                     ConcreteA1  ConcreteB1  (family 1)
 ConcreteFactory1 --------> creates both
 ConcreteFactory2 --------> ConcreteA2, ConcreteB2 (family 2)
```

## Participants
- **AbstractFactory** — declares creation methods for each product in the family.
- **ConcreteFactory** — produces one consistent family.
- **AbstractProduct / ConcreteProduct** — the products themselves.

## Pros
- Guarantees product-family consistency.
- Isolates concrete classes; swapping families is a one-line change.

## Cons / Trade-offs
- Adding a new *product type* to the family means changing every factory.
- More moving parts than a single Factory Method.

## Common Pitfalls (what interviewers probe)
- Using it when you only have one product — that's just Factory Method.
- Leaking concrete types out of the factory, defeating the purpose.

## Spring Boot / Framework Sightings
- `@ConditionalOnProperty` selecting which family of beans to wire (e.g. Postgres vs H2 persistence stack).
- Auto-configuration classes producing matched sets of beans per environment.

## Files in this folder
- **`AbstractFactoryDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`AbstractFactorySpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** When would you reach for Abstract Factory over Factory Method?
>
> **A:** When objects must be created as a matched set. A cross-platform UI kit is the textbook case: the factory guarantees the button, checkbox, and menu all belong to the same theme. Choosing a factory chooses the whole family.


## Related Patterns
Factory Method, Builder, Singleton

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
