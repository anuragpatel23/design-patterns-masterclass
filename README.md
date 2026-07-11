# 🏛️ Design Patterns — The Golden Source (Java + Spring Boot)

> An interview-preparation guide built for **senior / 20-years-experience** design-pattern interviews.
> 27 patterns — the 23 Gang-of-Four classics plus 4 enterprise/architectural patterns essential at architect level.
> Every pattern has its own folder with a deep README, a **runnable pure-Java** example, and an **idiomatic Spring Boot** example.
> A dedicated [`interview-questions/`](./interview-questions/) folder holds a 50-question answer-first bank.

---

## 📁 How this guide is organised

```
design-patterns-guide/
├── README.md                        ← you are here (index + how-to-use)
├── 01-singleton/
│   ├── README.md                    ← intent, analogy, structure, pros/cons, pitfalls, soundbites
│   ├── SingletonDemo.java           ← pure Java, self-contained, has main() — run it
│   └── SingletonSpringExample.java  ← how it shows up in Spring Boot
├── 02-factory-method/
│   └── ...
├── ...  (27 pattern folders)
└── interview-questions/
    └── README.md                    ← 50 Q&A: concepts, X-vs-Y traps, Java, Spring, scenarios
```

Each pattern folder is **numbered by its position in the reference guide** and **self-contained** — read it in isolation or work top to bottom.

---

## 🗂️ Pattern Index

### 🟢 Creational

| # | Pattern | One-liner |
|---|---|---|
| 1 | [Singleton](./01-singleton/) | Ensure a class has exactly one instance and provide a global access point to it. |
| 2 | [Factory Method](./02-factory-method/) | Define an interface for creating an object, but let subclasses/config decide which class to instantiate. |
| 3 | [Abstract Factory](./03-abstract-factory/) | Provide an interface for creating families of related objects without specifying their concrete classes. |
| 4 | [Builder](./04-builder/) | Separate the construction of a complex object from its representation for step-by-step, validated, immutable creation. |
| 18 | [Prototype](./18-prototype/) | Create new objects by cloning an existing instance rather than constructing from scratch. |
| 25 | [Object Pool](./25-object-pool/) | Reuse a fixed set of expensive-to-create objects instead of allocating and discarding them repeatedly. |

### 🔵 Structural

| # | Pattern | One-liner |
|---|---|---|
| 7 | [Decorator](./07-decorator/) | Attach additional responsibilities to an object dynamically by wrapping it. |
| 8 | [Proxy](./08-proxy/) | Provide a surrogate or placeholder for another object to control access to it. |
| 9 | [Adapter](./09-adapter/) | Convert the interface of a class into another interface clients expect. |
| 13 | [Facade](./13-facade/) | Provide a unified, simplified interface to a set of interfaces in a subsystem. |
| 14 | [Composite](./14-composite/) | Compose objects into tree structures and treat individual objects and compositions uniformly. |
| 19 | [Flyweight](./19-flyweight/) | Share fine-grained objects to support large numbers of them efficiently by separating intrinsic from extrinsic state. |

### 🟠 Behavioral

| # | Pattern | One-liner |
|---|---|---|
| 5 | [Observer](./05-observer/) | Define a one-to-many dependency so that when one object changes state, all its dependents are notified automatically. |
| 6 | [Strategy](./06-strategy/) | Define a family of interchangeable algorithms and select one at runtime. |
| 10 | [Template Method](./10-template-method/) | Define the skeleton of an algorithm in a base class, deferring specific steps to subclasses. |
| 11 | [Command](./11-command/) | Encapsulate a request as an object, enabling queuing, logging, and undo. |
| 12 | [Chain of Responsibility](./12-chain-of-responsibility/) | Pass a request along a chain of handlers until one handles it. |
| 15 | [State](./15-state/) | Allow an object to alter its behavior when its internal state changes — it appears to change class. |
| 16 | [Iterator](./16-iterator/) | Provide a way to access elements of a collection sequentially without exposing its underlying representation. |
| 17 | [Mediator](./17-mediator/) | Centralize complex communications between related objects into a mediator, reducing direct coupling. |
| 20 | [Visitor](./20-visitor/) | Add new operations to an object structure without modifying the objects themselves. |
| 24 | [Null Object](./24-null-object/) | Provide a do-nothing object with neutral behavior in place of null to eliminate null checks. |
| 26 | [Memento](./26-memento/) | Capture and externalize an object's internal state so it can be restored later, without violating encapsulation. |
| 27 | [Interpreter](./27-interpreter/) | Define a grammar for a simple language and an interpreter that evaluates sentences in it. |

### 🟣 Enterprise / Architectural

| # | Pattern | One-liner |
|---|---|---|
| 21 | [Repository](./21-repository/) | Mediate between the domain and data-mapping layers using a collection-like interface for accessing domain objects. |
| 22 | [Unit of Work](./22-unit-of-work/) | Track changes made during a business transaction and commit them all atomically. |
| 23 | [Specification](./23-specification/) | Encapsulate a business rule as a composable, reusable predicate object. |

---

## 🚀 Running the pure-Java examples

Every `*Demo.java` file is self-contained with a `main()` method and a package matching its folder concept.

```bash
# from inside a pattern folder, e.g. 06-strategy/
javac StrategyDemo.java
java strategy.StrategyDemo
```

The `*Spring*.java` files are **illustrative** — they show idiomatic Spring wiring (annotations, injection, proxies)
and are meant to be read and lifted into a real Spring Boot project, not compiled standalone (they need the Spring/JPA classpath).

---

## 🌱 Every pattern's Spring Boot sighting (quick map)

| # | Pattern | Category | Where it lives in Spring Boot |
|---|---|---|---|
| 1 | Singleton | Creational | Every `@Component`/`@Service`/`@Repository` bean is a **singleton scope** bean by default — one instance per container. |
| 2 | Factory Method | Creational | `BeanFactory`, and every `@Bean` method in a `@Configuration` class is a factory method. |
| 3 | Abstract Factory | Creational | `@ConditionalOnProperty` selecting which family of beans to wire (e.g. Postgres vs H2 persistence stack). |
| 4 | Builder | Creational | Lombok `@Builder` / `@Value`. |
| 5 | Observer | Behavioral | `ApplicationEventPublisher` + `@EventListener` (+ `@Async` for non-blocking). |
| 6 | Strategy | Behavioral | `Map<String, Strategy>` injection (idiomatic selector). |
| 7 | Decorator | Structural | Spring AOP, `@Transactional`, `@Cacheable`, `@Async` — all decorate your bean via a proxy. |
| 8 | Proxy | Structural | `@Transactional`, `@Async`, `@Cacheable` create JDK dynamic proxies or CGLIB proxies. |
| 9 | Adapter | Structural | `@FeignClient` adapts a REST API to a Java interface. |
| 10 | Template Method | Behavioral | `JdbcTemplate`, `RestTemplate`, `TransactionTemplate` — you supply the varying callback; they own the skeleton. |
| 11 | Command | Behavioral | Spring Batch `Step`/`Tasklet`, `TaskExecutor` submitting `Runnable`s. |
| 12 | Chain of Responsibility | Behavioral | Servlet `FilterChain`, Spring Security filter chain, Spring MVC `HandlerInterceptor` chain. |
| 13 | Facade | Structural | Your `@Service` layer is usually a facade over repositories + external clients. |
| 14 | Composite | Structural | `CompositePropertySource`, Spring Security's composite filter/authorization structures. |
| 15 | State | Behavioral | Spring Statemachine; Spring Batch `JobExecution`/`StepExecution` states. |
| 16 | Iterator | Behavioral | `Iterable`/`Iterator` everywhere; Spring Data `Page<T>`/`Slice<T>`; Spring Batch `ItemReader`. |
| 17 | Mediator | Behavioral | `ApplicationEventPublisher` as an in-process mediator; a message broker (Kafka) as a distributed mediator. |
| 18 | Prototype | Creational | `@Scope("prototype")` beans — a fresh instance on every injection/lookup. |
| 19 | Flyweight | Structural | `Integer.valueOf` caching (-128..127), `String` intern pool. |
| 20 | Visitor | Behavioral | AST/bytecode visitors (ASM), JPA Criteria tree processing, report/export generators. |
| 21 | Repository | Enterprise | `JpaRepository`/`CrudRepository` — Spring Data generates the implementation from the interface. |
| 22 | Unit of Work | Enterprise | `@Transactional` + the JPA persistence context IS a Unit of Work — dirty checking + a single flush at commit. |
| 23 | Specification | Enterprise | Spring Data `Specification<T>` + `JpaSpecificationExecutor` (composable, translated to SQL predicates). |
| 24 | Null Object | Behavioral | `Optional.orElse(new NoOpX())`; default no-op beans; `NoOpPasswordEncoder` (deprecated but illustrative). |
| 25 | Object Pool | Creational | HikariCP connection pool (the canonical example). |
| 26 | Memento | Behavioral | Saga compensating-state snapshots; workflow-engine checkpoints; editor/form undo stacks. |
| 27 | Interpreter | Behavioral | Spring Expression Language (SpEL), regex engines, SQL parsers are larger relatives. |

---

## 🧭 Choosing a pattern (decision guide)

```
What is the FORCE you are fighting?

  OBJECT CREATION is awkward / costly / conditional
      → Creational: Singleton, Factory Method, Abstract Factory, Builder, Prototype, Object Pool

  COMPOSING / ADAPTING objects and interfaces
      → Structural: Adapter, Decorator, Proxy, Facade, Composite, Flyweight

  BEHAVIOR branches, or COMMUNICATION between objects is tangled
      → Behavioral: Strategy, State, Observer, Mediator, Command,
                     Chain of Responsibility, Template Method, Iterator,
                     Visitor, Memento, Interpreter, Null Object

  DECOUPLING DOMAIN from PERSISTENCE / INFRASTRUCTURE
      → Enterprise: Repository, Unit of Work, Specification
      → (distributed consistency → Saga, Outbox, CQRS — see the reference guide's Part VI)

Then ask: "What VARIES?"  → isolate exactly that behind an abstraction (Open/Closed).
Then apply YAGNI: introduce the pattern when the 2nd/3rd real variation arrives.
```

---

## 🎯 How to prepare (suggested 5-day plan)

1. **Day 1 — Creational + Structural fundamentals.** Run every `*Demo.java`; read each README's *Pitfalls* and *Soundbites*.
2. **Day 2 — Behavioral.** Focus on the *X-vs-Y* distinctions (Strategy/State, Decorator/Proxy/Adapter, Observer/Mediator).
3. **Day 3 — Enterprise + Spring mapping.** Internalise the Spring-sighting table above; understand the `@Transactional` proxy model.
4. **Day 4 — Interview bank.** Work through [`interview-questions/`](./interview-questions/) out loud, covering answers first.
5. **Day 5 — Scenarios.** Do the scenario questions (§9) as whiteboard designs, always naming the trade-off and the exit condition.

---

## ⚖️ The senior mindset (what actually gets you the offer)
- **Lead with the problem, then the pattern.** Patterns are targets of refactoring, not starting points.
- **Always name the trade-off and when you would NOT use it.** This is the single biggest differentiator.
- **Anchor in framework reality.** Recognise the pattern *inside* Spring/JDK rather than re-implementing it.
- **Know the modern-Java moves** (lambdas, records, sealed types, `Optional`) that turn some patterns into obsolete boilerplate.

---

*Golden source · 27 patterns · Java 21 + Spring Boot · pair each folder with its matching questions in the interview bank.*
