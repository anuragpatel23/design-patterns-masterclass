# 19. Flyweight Pattern

**Category:** 🔵 Structural

> Share fine-grained objects to support large numbers of them efficiently by separating intrinsic from extrinsic state.

---

## Intent
Use sharing to support large numbers of fine-grained objects efficiently by splitting state into intrinsic (shared, stored in the flyweight) and extrinsic (contextual, passed in by clients).

## Real-World Analogy
A **text editor.** There are millions of characters on screen but only ~100 distinct glyphs. The font/shape of the letter 'a' (intrinsic) is stored once and shared; its position on the page (extrinsic) is supplied per use.

## When to Use
- You must create a very large number of similar objects and memory is a concern.
- Most object state can be made extrinsic (moved out and passed in).
- Object identity isn't required — shared instances are acceptable.

## Structure
```
FlyweightFactory (cache) -> returns shared Flyweight(intrinsic)
Client supplies extrinsic state at call time.
```

## Participants
- **Flyweight** — stores intrinsic (shared) state; operations take extrinsic state as params.
- **FlyweightFactory** — caches and reuses flyweights.

## Pros
- Dramatic memory savings for many similar objects.

## Cons / Trade-offs
- Complexity of separating intrinsic vs extrinsic state.
- Trades CPU (passing extrinsic state) for memory.

## Common Pitfalls (what interviewers probe)
- Making flyweights mutable — shared mutable state is a bug magnet; keep them immutable.
- Applying it prematurely; only worthwhile at large scale.

## Spring Boot / Framework Sightings
- `Integer.valueOf` caching (-128..127), `String` intern pool.
- `@Cacheable` sharing computed results; connection pools share heavy resources.

## Files in this folder
- **`FlyweightDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`FlyweightSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Explain intrinsic vs extrinsic state.
>
> **A:** Intrinsic state is context-independent and shareable, so it's stored inside the flyweight — like a glyph's shape. Extrinsic state is context-dependent and must be supplied by the client on each call — like the glyph's screen coordinates. Flyweight works by maximizing intrinsic sharing and externalizing the rest.


## Related Patterns
Singleton, Object Pool, Factory Method

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
