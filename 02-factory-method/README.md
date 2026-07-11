# 02. Factory Method Pattern

**Category:** 🟢 Creational

> Define an interface for creating an object, but let subclasses/config decide which class to instantiate.

---

## Intent
Define an interface for creating an object but defer the choice of concrete class to subclasses or configuration, so client code depends on an abstraction rather than on `new ConcreteType()`.

## Real-World Analogy
A **logistics company** that first only ships by truck. When sea shipping is added, you don't rewrite the client — a `LogisticsFactory` returns the right transport (Truck or Ship). Client code always calls `deliver()` on the abstraction.

## When to Use
- You don't know ahead of time which concrete class you need.
- You want to give a framework a hook to extend which objects it creates.
- You want to centralize and name creation logic, replacing scattered `new` calls / switch statements.

## Structure
```
Creator                    Product (interface)
 + factoryMethod(): Product   ^
         ^                    |
 ConcreteCreator ---creates--> ConcreteProduct
```

## Participants
- **Product** — the interface of objects the factory creates.
- **ConcreteProduct** — concrete implementations.
- **Creator / Factory** — declares the factory method returning a Product.

## Pros
- Decouples client from concrete classes (Dependency Inversion).
- Adds new product types without touching client code (Open/Closed).
- Single, testable place for creation + validation logic.

## Cons / Trade-offs
- Can proliferate small classes/subclasses.
- Indirection can obscure what is actually being created.

## Common Pitfalls (what interviewers probe)
- Confusing Factory Method (one product, subclass decides) with Abstract Factory (families of products).
- A giant `switch` inside the factory — acceptable, but a `Map<key, Supplier>` or Spring `Map<String,Bean>` scales better.

## Spring Boot / Framework Sightings
- `BeanFactory`, and every `@Bean` method in a `@Configuration` class is a factory method.
- `Map<String, Interface>` injection lets Spring build a runtime factory keyed by bean name.

## Files in this folder
- **`FactoryMethodDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`FactoryMethodSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Difference between Factory Method and Abstract Factory?
>
> **A:** Factory Method produces one product and uses inheritance/config to pick the concrete type. Abstract Factory produces a whole family of related products and uses composition — you pick a factory, and it yields a matched set.

> **Q:** How do you implement a factory idiomatically in Spring?
>
> **A:** Inject `Map<String, ServiceInterface>`; Spring populates it with all beans of that type keyed by bean name. The factory just looks up by a runtime key — no switch, and new implementations register themselves automatically.


## Related Patterns
Abstract Factory, Template Method, Prototype

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
