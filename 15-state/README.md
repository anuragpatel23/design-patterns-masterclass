# 15. State Pattern

**Category:** 🟠 Behavioral

> Allow an object to alter its behavior when its internal state changes — it appears to change class.

---

## Intent
Allow an object to alter its behavior when its internal state changes; the object appears to change its class as state transitions occur.

## Real-World Analogy
A **traffic light.** Green behaves one way and transitions to Yellow; Yellow to Red; Red to Green. Each state knows how to behave and what comes next; the light delegates behavior to its current state.

## When to Use
- An object's behavior depends on its state and must change at runtime.
- You have large conditionals that branch on an object's state field.
- State transitions are well-defined (order, payment, workflow lifecycles).

## Structure
```
Context -> State (handle())
               ^
   ConcreteStateA -> transitions to -> ConcreteStateB
```

## Participants
- **Context** — holds current state, delegates to it.
- **State** — interface for state-specific behavior.
- **ConcreteState** — implements behavior and transitions.

## Pros
- Removes big state conditionals; each state is isolated and testable.
- Explicit, discoverable transitions.

## Cons / Trade-offs
- More classes; transitions spread across states can be hard to see holistically.

## Common Pitfalls (what interviewers probe)
- Confusing State with Strategy — State transitions between behaviors internally; Strategy is picked externally.
- Illegal transitions not being rejected (enforce a legal transition map).

## Spring Boot / Framework Sightings
- Spring Statemachine; Spring Batch `JobExecution`/`StepExecution` states.
- Domain workflow engines (order lifecycle).

## Files in this folder
- **`StateDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`StateSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** When would you choose State over a simple enum + switch?
>
> **A:** When the number of states and transitions grows and each state has meaningful behavior and rules. The switch approach scatters state logic and makes illegal transitions easy. The State pattern localizes each state's behavior and its allowed transitions, which is far more maintainable for real workflows.


## Related Patterns
Strategy, Command, Observer

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
