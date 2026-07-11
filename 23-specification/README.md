# 23. Specification Pattern

**Category:** 🟣 Enterprise / Architectural

> Encapsulate a business rule as a composable, reusable predicate object.

---

## Intent
Encapsulate a business rule in a predicate-like object that can be combined (and/or/not) and reused for validation, selection, and querying, keeping rules out of scattered conditionals.

## Real-World Analogy
A **dating-app filter.** 'Within 10 miles AND non-smoker AND likes hiking.' Each criterion is a reusable filter; you compose them with AND/OR/NOT to build arbitrarily complex match rules — and reuse the same building blocks elsewhere.

## When to Use
- Business selection rules are complex, reused, and combined in different ways.
- You want the same rule usable for in-memory filtering AND database querying.
- You want to name and test rules independently of where they're applied.

## Structure
```
Specification.isSatisfiedBy(candidate)
+ and(), or(), not() combinators
```

## Participants
- **Specification** — `isSatisfiedBy(T)` plus `and/or/not` combinators.
- **Composite specs** — And/Or/Not compositions.

## Pros
- Reusable, named, testable rules; composable; removes duplicated conditionals.

## Cons / Trade-offs
- More classes; can be overkill for one-off simple checks.

## Common Pitfalls (what interviewers probe)
- Duplicating a rule for in-memory vs DB — Spring Data `Specification<T>` translates to SQL so you write it once.
- Specs that reach into infrastructure, breaking reusability.

## Spring Boot / Framework Sightings
- Spring Data `Specification<T>` + `JpaSpecificationExecutor` (composable, translated to SQL predicates).

## Files in this folder
- **`SpecificationDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`SpecificationSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Why Specification over just writing query methods?
>
> **A:** Derived query methods explode combinatorially — findByRegionAndTierAndActive... becomes unmaintainable. Specifications let you define atomic, named, testable predicates and compose them with and/or/not at runtime, and Spring Data's Specification<T> translates the composition into a single SQL query. Same rule works for validation and querying.


## Related Patterns
Repository, Interpreter, Strategy

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
