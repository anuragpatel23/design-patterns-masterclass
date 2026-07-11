# 24. Null Object Pattern

**Category:** 🟠 Behavioral

> Provide a do-nothing object with neutral behavior in place of null to eliminate null checks.

---

## Intent
Provide an object with defined neutral ('do nothing') behavior to represent the absence of a real object, so clients don't need explicit null checks.

## Real-World Analogy
A **substitute teacher who follows a 'do nothing disruptive' script.** When the real teacher is absent, class still runs safely with the substitute's neutral behavior — no one has to check 'is there a teacher?' before proceeding.

## When to Use
- You want to avoid scattering `if (x != null)` checks.
- A sensible neutral/no-op default behavior exists for a missing collaborator.
- You want polymorphism to handle the 'absent' case uniformly.

## Structure
```
Client -> Service (interface)
                ^          ^
         RealService   NullService (no-op)
```

## Participants
- **AbstractOperation** — the interface.
- **RealObject** — real behavior.
- **NullObject** — neutral, safe no-op implementation.

## Pros
- Removes null checks; simplifies client code; avoids NPEs.

## Cons / Trade-offs
- Can hide real errors (silently doing nothing when something went wrong).
- Not suitable when 'absent' must be handled distinctly.

## Common Pitfalls (what interviewers probe)
- Masking bugs — sometimes a missing dependency SHOULD fail loudly.
- Overusing it where `Optional<T>` communicates intent more clearly.

## Spring Boot / Framework Sightings
- `Optional.orElse(new NoOpX())`; default no-op beans; `NoOpPasswordEncoder` (deprecated but illustrative).

## Files in this folder
- **`NullObjectDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`NullObjectSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Null Object vs Optional?
>
> **A:** Optional makes absence explicit and forces the caller to decide what to do; Null Object makes absence invisible by supplying safe default behavior. Use Null Object when a neutral behavior is genuinely correct (e.g., a no-op logger). Use Optional when the caller must consciously handle the missing case. Don't use Null Object to paper over what should be an error.


## Related Patterns
Strategy, Factory Method, Singleton

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
