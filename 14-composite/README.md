# 14. Composite Pattern

**Category:** 🔵 Structural

> Compose objects into tree structures and treat individual objects and compositions uniformly.

---

## Intent
Compose objects into part-whole tree hierarchies and let clients treat individual objects (leaves) and compositions (branches) through the same interface.

## Real-World Analogy
A **file system.** A folder can contain files and other folders. Whether you ask a single file or a whole folder for its size, you use the same operation — the folder just sums up its children.

## When to Use
- You have a tree/part-whole hierarchy (menus, org charts, file systems, UI component trees).
- Clients should ignore the difference between a leaf and a composite.
- Operations should recurse naturally over the structure.

## Structure
```
Component (operation())
   ^                 ^
Leaf            Composite (children: List<Component>)
                    delegates to children
```

## Participants
- **Component** — common interface for leaves and composites.
- **Leaf** — a primitive with no children.
- **Composite** — holds children and implements operations by delegating to them.

## Pros
- Uniform treatment of individual and grouped objects.
- Easy to add new component types; natural recursion.

## Cons / Trade-offs
- Can make the design overly general; type-safety of children is looser.

## Common Pitfalls (what interviewers probe)
- Deciding where child-management methods (`add`/`remove`) live — on Component (uniform but unsafe on leaves) vs only Composite (safe but non-uniform).
- Cycles in the graph turning your 'tree' into infinite recursion.

## Spring Boot / Framework Sightings
- `CompositePropertySource`, Spring Security's composite filter/authorization structures.
- Any tree of validators/handlers you fold over.

## Files in this folder
- **`CompositeDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`CompositeSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** What's the core trade-off in Composite?
>
> **A:** Transparency vs safety. Put add()/remove() on the Component interface and clients treat everything uniformly, but calling add() on a leaf is meaningless at runtime. Put them only on Composite and you get type-safety but lose uniformity. Most codebases choose transparency and throw UnsupportedOperationException on leaves.


## Related Patterns
Decorator, Iterator, Visitor

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
