# Design Patterns — Interview Question Bank (Architect Level)

> A curated, answer-first question bank for senior / 20-years-experience design-pattern interviews.
> Organised so you can drill by theme. Each answer is written the way you'd actually *say* it in a room —
> crisp, with the trade-off named. Where code helps, it's Java + Spring Boot.

---

## How to use this bank
1. **Cover the answer, say yours out loud, then compare.** Interviews test articulation, not recognition.
2. **Always name the trade-off.** Senior candidates are distinguished by "here's when I would *not* use this."
3. **Tie to real framework code.** "@Transactional is a proxy (Proxy + Decorator)" beats reciting the GoF diagram.

---

## Table of Contents
1. [Fundamentals & Classification](#1-fundamentals--classification)
2. [The Classic "X vs Y" Traps](#2-the-classic-x-vs-y-traps)
3. [Creational — Deep Dives](#3-creational--deep-dives)
4. [Structural — Deep Dives](#4-structural--deep-dives)
5. [Behavioral — Deep Dives](#5-behavioral--deep-dives)
6. [Enterprise / Architectural](#6-enterprise--architectural)
7. [Java-Language-Specific](#7-java-language-specific)
8. [Spring Boot-Specific](#8-spring-boot-specific)
9. [Scenario / System-Design-Flavoured](#9-scenario--system-design-flavoured)
10. [SOLID, Anti-Patterns & Judgement](#10-solid-anti-patterns--judgement)
11. [Rapid-Fire One-Liners](#11-rapid-fire-one-liners)

---

## 1. Fundamentals & Classification

**Q1. What is a design pattern, and what is it *not*?**
A design pattern is a named, reusable solution to a recurring design problem — a template you adapt, not a finished library you drop in. It is *not* an algorithm (which is a precise computational recipe) and *not* a framework (which is a partial application you extend). Its real value is shared vocabulary: saying "wrap it in a Decorator" transmits intent, structure, and trade-offs in three words.

**Q2. What are the three GoF categories and the one-line litmus for each?**
- **Creational** — *how objects are created* (decouple instantiation): Singleton, Factory Method, Abstract Factory, Builder, Prototype, (+ Object Pool).
- **Structural** — *how objects are composed* (assemble into larger structures): Adapter, Decorator, Proxy, Facade, Composite, Flyweight, Bridge.
- **Behavioral** — *how objects communicate/assign responsibility*: Strategy, Observer, Command, Template Method, Chain of Responsibility, State, Iterator, Mediator, Visitor, Memento, Interpreter, (+ Null Object, Specification).

**Q3. Are design patterns still relevant with modern languages and Spring?**
Yes, but the *implementation* often disappears into the language or framework. Lambdas collapse Strategy/Command to a method reference; `Optional` competes with Null Object; sealed types + pattern matching rival Visitor; Spring's container *is* Singleton/Factory/Proxy. The **problems** patterns solve are permanent even when the boilerplate is gone. The senior move is recognising the pattern *inside* the framework, not re-implementing it.

**Q4. How do you choose a pattern? Walk me through your decision process.**
Start from the *force* you're fighting: creation cost/complexity → Creational; incompatible or bloated interfaces → Structural; branching behavior or tangled communication → Behavioral. Then ask "what varies?" and isolate exactly that axis of change behind an abstraction (this is the Open/Closed heartbeat of most patterns). Finally, apply YAGNI — introduce the pattern when the second or third variation actually arrives, not speculatively.

---

## 2. The Classic "X vs Y" Traps

These separate seniors from mid-levels. Interviewers *love* them.

**Q5. Strategy vs State.**
Structurally near-identical (an object delegating to a swappable implementation). Difference is *who* changes it and *why*: **Strategy** is chosen by the client and doesn't change itself — "how do I compute this?". **State** is chosen by the object based on internal condition, and states *transition to one another* — "what am I now, what can I become?". A payment-method selector is Strategy; an order lifecycle (NEW→PAID→SHIPPED) is State.

**Q6. Decorator vs Proxy vs Adapter.**
All wrap another object; **intent** differs. **Decorator** — same interface, *adds behavior* (and stacks). **Proxy** — same interface, *controls access* (lazy, security, remoting, caching). **Adapter** — *changes the interface* so an incompatible class fits. Mnemonic: Decorator enhances, Proxy guards, Adapter translates.

**Q7. Factory Method vs Abstract Factory.**
**Factory Method** creates *one* product; the concrete type is chosen via subclass/config (inheritance). **Abstract Factory** creates a *family* of related products that must match; you pick a factory (composition) and get a consistent set. One product → Factory Method; matched set → Abstract Factory.

**Q8. Adapter vs Facade.**
**Adapter** makes an existing interface match what a client expects (compatibility), usually wrapping one class. **Facade** invents a *new, simpler* interface over a whole subsystem (simplification). Adapter conforms to an existing contract; Facade defines a fresh, convenient one.

**Q9. Observer vs Mediator vs Pub/Sub.**
**Observer** — subject broadcasts to observers it doesn't know individually (one-to-many, direct references). **Mediator** — colleagues route interactions through a central coordinator (collapses many-to-many into many-to-one). **Pub/Sub** — Observer decoupled further by a broker/topic so publisher and subscriber never reference each other, often across processes (Kafka). They're a spectrum of increasing decoupling.

**Q10. Template Method vs Strategy.**
Both vary part of an algorithm. **Template Method** fixes the *skeleton* in a base class and defers steps via inheritance ("don't call us, we'll call you"). **Strategy** swaps the *whole* algorithm via composition/delegation. Prefer Strategy when steps vary independently and you want to avoid inheritance rigidity.

**Q11. Composite vs Decorator.**
Both are recursive and share an interface. **Composite** builds *part-whole trees* and treats leaves and branches uniformly (a *has-many* of children). **Decorator** wraps *exactly one* component to add behavior (a *has-one*). Same recursive shape, different cardinality and intent.

**Q12. Builder vs Factory.**
**Factory** decides *which* object to create and returns it in one shot. **Builder** constructs *one complex* object *step by step*, often immutable, with validation at `build()`. Use Builder for many-optional-field construction; Factory for polymorphic type selection.

---

## 3. Creational — Deep Dives

**Q13. Give the safest Singleton in Java and defend it.**
The **enum singleton**: `enum Config { INSTANCE; }`. It's thread-safe by class-init semantics, and the JVM guarantees no second instance via reflection or deserialization — the two attack vectors that break hand-rolled singletons. For lazy heavy init, the **initialization-on-demand holder idiom** gives lazy, lock-free thread safety.

**Q14. Why is the "double-checked locking" singleton famously broken without `volatile`?**
Without `volatile`, the writing thread can publish a *non-null but partially constructed* reference due to instruction reordering (the object reference is assigned before the constructor finishes). Another thread sees non-null, skips the lock, and uses a half-built object. `volatile` establishes a happens-before edge that forbids that reordering.

**Q15. When is Singleton an anti-pattern, and what do you use instead?**
When it smuggles in global mutable state and hidden dependencies — it hurts testability, couples code to a static accessor, and can hide concurrency bugs. In Spring you almost never need it: declare a bean (singleton-scoped by default) and *inject* it. Explicit dependency, container-managed lifecycle, trivially mockable in tests.

**Q16. How do you build a factory without a giant switch?**
Register creators in a `Map<Key, Supplier<T>>` (pure Java) or, in Spring, inject `Map<String, Interface>` — the container populates it with all beans of that type keyed by name. New implementations self-register; the factory never changes. That's Open/Closed in practice.

**Q17. Java's `clone()` is disliked — how do you implement Prototype properly?**
Avoid `Cloneable`/`Object.clone()` (broken contract, shallow by default, checked exception). Prefer a **copy constructor** or a `copy()` method that performs an explicit deep copy of mutable inner state. For deep graphs, a serialization round-trip or a mapping library is pragmatic.

---

## 4. Structural — Deep Dives

**Q18. Show how `java.io` is a Decorator showcase.**
`new BufferedReader(new InputStreamReader(new FileInputStream(f)))` — each wrapper shares the `Reader`/`InputStream` contract and adds one behavior (buffering, decoding, file access). You compose behaviors at runtime by nesting; the base stream never changes. Textbook Decorator.

**Q19. Explain the `@Transactional` self-invocation gotcha in Proxy terms.**
Spring wraps the bean in a proxy that applies the transaction advice. An internal `this.otherMethod()` call goes directly to the target object, bypassing the proxy, so the advice never runs and `@Transactional`/`@Async`/`@Cacheable` are silently ignored. Fixes: call through the injected bean reference, self-inject the bean, or move the annotated method to a separate bean.

**Q20. When would a Facade become a liability?**
When it grows into a **god object** — a dumping ground of unrelated methods that couples everything to it and hides capabilities callers legitimately need. Keep facades intention-focused (one cohesive subsystem) and don't forbid direct subsystem access when advanced callers need it.

**Q21. Flyweight — intrinsic vs extrinsic, and when it backfires.**
Intrinsic = shareable, context-free state stored in the flyweight (a glyph's shape). Extrinsic = context state supplied per call (screen position). It backfires when objects are cheap: modern JVM allocation + generational GC make small-object churn nearly free, so the added complexity and shared-mutable-state risk cost more than they save. Reserve it for genuinely large populations of similar objects.

---

## 5. Behavioral — Deep Dives

**Q22. Implement undo. Which patterns, and how do they combine?**
**Command** (each action is an object with `execute()`/`undo()`) plus a history stack in the invoker. For rich state, **Memento** snapshots the originator opaquely. Production systems usually store *diffs* (reverse commands) rather than full snapshots — a Command/Memento hybrid — to bound memory.

**Q23. What is double dispatch and why does Visitor need it?**
The executed method depends on the runtime types of *two* objects (element and visitor). Java dispatches only on the receiver, so Visitor simulates it: `element.accept(visitor)` dispatches on the element, then calls `visitor.visit(this)`, dispatching on the visitor with the element's concrete static type. That two-step resolves the right operation for the right element.

**Q24. Visitor's dual trade-off — and the modern Java alternative.**
Visitor makes adding *operations* cheap but adding *element types* expensive (every visitor must change). If your type hierarchy changes more than your operations, it's the wrong pattern. **Java 21** sealed interfaces + exhaustive `switch` pattern matching give compiler-checked handling of a closed type set without accept/visit boilerplate.

**Q25. Chain of Responsibility — name a real framework instance and the main risk.**
Spring Security's filter chain / the Servlet `FilterChain`: each filter handles or calls `chain.doFilter()` to pass control on. Main risk: a request falling off the end **unhandled** — always provide a terminal/default handler and guard against cycles.

**Q26. Null Object vs Optional — when each?**
Use **Null Object** when a *neutral behavior is genuinely correct* (a no-op logger, a zero-discount policy) and you want to erase null checks via polymorphism. Use **Optional** when the caller *must consciously decide* what "absent" means. Never use Null Object to silently swallow what should be a loud error.

---

## 6. Enterprise / Architectural

**Q27. Is Spring Data's `JpaRepository` "the Repository pattern"?**
It's a strong implementation, but it leaks persistence concepts (`Pageable`, ORM types) into what a purist wants as a *domain* interface. In hexagonal/clean architecture you define a domain-facing **port** in domain language, then implement it *using* Spring Data underneath — keeping domain logic ignorant of JPA while still enjoying Spring's productivity.

**Q28. Where is Unit of Work in a Spring app?**
The JPA **persistence context under `@Transactional`**. Managed entities are change-tracked (dirty checking); at commit Hibernate computes the minimal writes and flushes them atomically. You rarely call `save()` per field change — the persistence context *is* the Unit of Work. Watch out for side effects (emails/HTTP) inside the transaction that a rollback can't undo.

**Q29. Why Specification over a pile of `findByAAndBAndC` methods?**
Derived queries explode combinatorially and can't be composed at runtime. Specifications define *atomic, named, testable* predicates combined with `and/or/not`; Spring Data's `Specification<T>` translates the composition into a **single SQL query**. Same rule serves validation and querying. It also keeps business rules out of scattered `if`s.

**Q30. Distinguish the DDD/distributed patterns your guide references (Saga, Outbox, CQRS).**
- **Saga** — coordinate a business transaction across services via a sequence of local transactions + compensating actions (no distributed 2PC).
- **Transactional Outbox** — write the domain change and an outgoing message in the *same* DB transaction, then relay the message, guaranteeing at-least-once publish without dual-write inconsistency.
- **CQRS** — separate the write model from read models so each is optimised independently. These sit above GoF patterns and are the architect-level extension.

---

## 7. Java-Language-Specific

**Q31. Which patterns did Java lambdas/functional interfaces make nearly invisible?**
Strategy (`Comparator`, any `Function`/`Predicate`), Command (`Runnable`/`Callable`), Template Method callbacks (`forEach`, `computeIfAbsent`), and simple Observers (a `Consumer<Event>`). The pattern is still there conceptually; the class boilerplate is gone.

**Q32. Name concrete Gang-of-Four patterns living in the JDK.**
Singleton (`Runtime.getRuntime()`), Factory Method (`Calendar.getInstance()`, `valueOf`), Builder (`StringBuilder`, `Stream.Builder`), Decorator (`java.io` streams, `Collections.unmodifiableList`), Adapter (`Arrays.asList`, `Collections.list`), Iterator (`Iterator`/`Iterable`), Observer (the deprecated `java.util.Observer`; modern `Flow`/reactive), Proxy (`java.lang.reflect.Proxy`, dynamic proxies), Prototype (`Object.clone`), Flyweight (`Integer.valueOf` cache, `String.intern`), Template Method (`AbstractList`, `AQS`).

**Q33. How do `record`, `sealed`, and pattern matching change pattern usage in modern Java?**
`record` removes Builder/Value-object boilerplate for immutable data. `sealed` + `switch` pattern matching provide exhaustive, compiler-checked alternatives to Visitor and to type-switch conditionals. `Optional` reshapes Null Object decisions. The net effect: fewer hand-written pattern classes, more patterns expressed idiomatically in the language.

**Q34. Is `enum` singleton really immune to reflection and serialization attacks?**
Yes. The JVM forbids reflective instantiation of enum constants (`Constructor.newInstance` throws for enums), and enum serialization is handled specially by name — deserialization returns the existing constant rather than constructing a new one. That's why Effective Java recommends it.

---

## 8. Spring Boot-Specific

**Q35. Map the everyday Spring features to their underlying GoF patterns.**
- `@Component`/`@Service` beans → **Singleton** (default scope).
- `@Bean` methods, `BeanFactory` → **Factory Method**.
- `@Transactional`, `@Cacheable`, `@Async`, AOP advice → **Proxy + Decorator**.
- `ApplicationEventPublisher` / `@EventListener` → **Observer** (and in-process **Mediator**).
- `Map<String, Interface>` injection → **Strategy / Factory** selector.
- `JdbcTemplate`, `RestTemplate`, `TransactionTemplate` → **Template Method**.
- Spring Security filter chain → **Chain of Responsibility**.
- `@FeignClient` → **Adapter / Remote Proxy**.
- `@Scope("prototype")` → **Prototype** (lifecycle sense).
- HikariCP → **Object Pool**. `Page<T>` → **Iterator**. `Specification<T>` → **Specification**.

**Q36. How does Spring decide between JDK dynamic proxy and CGLIB?**
By default Spring uses a **JDK dynamic proxy** when the bean implements an interface, and **CGLIB** (subclass proxy) when it doesn't. Spring Boot defaults `proxyTargetClass=true` in many setups, favoring CGLIB. Implication: `final` classes/methods can't be CGLIB-proxied, and constructor logic runs twice-ish concerns apply — design beans to be proxy-friendly.

**Q37. You need a fresh prototype bean inside a singleton. How?**
Direct injection captures a single instance at construction. Use `ObjectProvider<T>` (`provider.getObject()` per call), `@Lookup` method injection, or inject the `ApplicationContext` and request the bean — each yields a new prototype instance on demand.

**Q38. Give a clean Spring implementation of Strategy that adds zero code when a new strategy appears.**
Define a strategy interface; annotate each implementation `@Component("key")`; inject `Map<String, Strategy>` into the context and look up by runtime key. Adding a new strategy is adding one class — the selector never changes. (See `06-strategy/StrategySpringExample.java`.)

**Q39. How would you let non-developers change business rules without a redeploy, in Spring?**
Store the rule as text (DB/config) and evaluate with **SpEL** (`SpelExpressionParser`) against a context object — the framework's Interpreter. Beyond simple grammars, use a rules engine (**Drools**). Model atomic conditions as **Specifications** if the rules also drive queries. (See `27-interpreter/InterpreterSpringExample.java`.)

---

## 9. Scenario / System-Design-Flavoured

**Q40. "Design a notification system supporting Email/SMS/Push, easily extended to new channels."**
Channel = **Strategy** behind a `NotificationService` interface; select via `Map<String, NotificationService>` (**Factory**). Cross-cutting concerns (retry, logging, rate-limit) as **Decorators**/AOP. Fan-out to multiple channels via **Observer** (`ApplicationEvent`) or a broker for scale. New channel = new `@Component`, zero changes elsewhere.

**Q41. "Design a payment system integrating multiple gateways (Stripe, PayPal, Razorpay)."**
Each gateway SDK behind an **Adapter** conforming to your `PaymentProcessor` port. Gateway selection via **Strategy/Factory**. Wrap with a **Proxy/Decorator** for idempotency, retries, and circuit-breaking. Orchestrate multi-step capture/refund with **Command** (undoable) and, across services, a **Saga** with compensating actions.

**Q42. "Design an order state machine."**
**State** pattern with an explicit legal-transition table; illegal transitions throw. Emit domain events on transition (**Observer**) for side effects. Persist within `@Transactional` (**Unit of Work**). For complex flows, `spring-statemachine`. Snapshot for rollback with **Memento**.

**Q43. "Build a configurable request-processing pipeline (auth → rate-limit → validate → handle)."**
**Chain of Responsibility**: ordered handlers (`@Order`), each handles or passes on. It mirrors Spring Security's filter chain. Make handlers beans so the pipeline is assembled by the container and reordered by configuration. Always include a terminal handler.

**Q44. "A service class has a 12-branch `switch` on `documentType`. Refactor it."**
Replace the switch with **Strategy** (`Map<DocumentType, DocumentHandler>`), each handler a bean. The switch becomes a lookup; new document types self-register. If the handlers share a fixed processing skeleton, layer **Template Method** for the common steps. This is the canonical Open/Closed refactor.

**Q45. "How do you keep domain logic testable without mocking Spring/JPA everywhere?"**
**Repository** (domain-facing ports) + **Unit of Work** boundaries + **Hexagonal architecture**: domain depends on interfaces (ports); infrastructure adapters implement them. Domain tests use in-memory fakes — no Spring context, no database, fast and deterministic.

---

## 10. SOLID, Anti-Patterns & Judgement

**Q46. Which SOLID principle does each pattern family most embody?**
Most patterns serve **Open/Closed** (extend without modifying) and **Dependency Inversion** (depend on abstractions). Strategy/Template Method/Decorator → OCP. Repository/Factory/Adapter → DIP. Visitor/SRP-driven decorators → **Single Responsibility**. Interface-segregated ports → **ISP**. Substitutable subclasses (Template Method done right) → **LSP**.

**Q47. What's "patternitis" and how do you avoid it?**
Over-applying patterns for their own sake — wrapping trivial code in factories, strategies, and decorators nobody needs, adding indirection that obscures intent. Avoid it with **YAGNI**: introduce a pattern when the second/third real variation arrives, keep the simplest thing that works, and let refactoring pull the pattern out when duplication demands it.

**Q48. When would you deliberately *reject* a "correct" pattern in a review?**
When it adds indirection without a real axis of change (speculative generality), when the team's cognitive load outweighs the benefit, when the language/framework already provides it more simply (lambda vs Strategy class, `Optional` vs Null Object), or when it hides errors that should surface. "Correct in the abstract" isn't "right for this code."

**Q49. How do patterns relate to refactoring?**
Patterns are frequent *targets* of refactoring, not starting points. You refactor *toward* a pattern when duplication or rigidity appears (e.g., "Replace Conditional with Polymorphism" lands you at Strategy/State). Fowler's catalog and GoF are complementary: refactorings are the moves, patterns are the destinations.

**Q50. Two engineers disagree: "this is a Decorator" vs "this is a Proxy." How do you settle it as the architect?**
By **intent**, not structure — both wrap the same interface. If the wrapper's purpose is to *add optional, stackable behavior*, it's a Decorator. If it *controls access* (lazy load, security, remoting, caching) and typically isn't stacked, it's a Proxy. Then I'd redirect: the label matters less than whether the abstraction is at the right seam and the code communicates intent.

---

## 11. Rapid-Fire One-Liners

- **Singleton** → one instance, global access. *Enum is safest.*
- **Factory Method** → subclass/config picks the concrete product.
- **Abstract Factory** → factory of matched product families.
- **Builder** → step-by-step immutable construction with validation.
- **Prototype** → clone an existing instance (copy constructor > `clone()`).
- **Object Pool** → reuse expensive objects (HikariCP).
- **Adapter** → translate an incompatible interface.
- **Decorator** → add stackable behavior, same interface.
- **Proxy** → control access, same interface.
- **Facade** → one simple door to a complex subsystem.
- **Composite** → treat trees and leaves uniformly.
- **Flyweight** → share intrinsic state across many objects.
- **Strategy** → interchangeable algorithms, chosen by client.
- **State** → behavior changes with internal state, self-transitioning.
- **Observer** → one-to-many auto notification.
- **Mediator** → central hub collapses many-to-many.
- **Command** → request as object; enables queue/log/undo.
- **Chain of Responsibility** → pass request down a handler pipeline.
- **Template Method** → fixed skeleton, subclass fills steps.
- **Iterator** → sequential access without exposing internals.
- **Visitor** → new operations without changing element classes (double dispatch).
- **Memento** → opaque snapshot for undo/rollback.
- **Interpreter** → evaluate a simple grammar (SpEL/Drools at scale).
- **Null Object** → safe no-op instead of null.
- **Repository** → collection-like domain view over persistence.
- **Unit of Work** → atomic commit of tracked changes (`@Transactional`).
- **Specification** → composable, reusable business-rule predicates.

---

## Closing advice for the room
- **Lead with the problem, then the pattern.** "The branching on type violates OCP, so I'd isolate it behind a Strategy."
- **Always volunteer the trade-off and the exit condition** ("...and I'd drop it if there's only ever one variant").
- **Anchor in framework reality.** Naming `@Transactional` as Proxy+Decorator, or the Spring Security filter chain as Chain of Responsibility, signals you've *shipped*, not just read the book.
- **Know the modern language moves** (lambdas, records, sealed types, `Optional`) that make some patterns obsolete boilerplate.

*Golden source — pair each answer with the runnable example in the matching numbered folder.*
