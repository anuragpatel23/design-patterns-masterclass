# 26. Memento Pattern

**Category:** 🟠 Behavioral

> Capture and externalize an object's internal state so it can be restored later, without violating encapsulation.

---

## Intent
Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later — the basis of undo/rollback.

## Real-World Analogy
A **video game save point.** You snapshot your progress; if things go wrong, you reload the save. The save file preserves your state, but the game's internals aren't exposed to you as the player.

## When to Use
- You need undo/redo or rollback of an object's state.
- You need checkpoints in a multi-step process.
- You want to snapshot state without exposing the object's internals to the caretaker.

## Structure
```
Originator -> creates -> Memento (opaque state)
Caretaker stores Mementos; Originator.restore(memento).
```

## Participants
- **Originator** — creates a memento of its state and restores from one.
- **Memento** — opaque snapshot; only the Originator reads its contents.
- **Caretaker** — keeps mementos (history) but never inspects them.

## Pros
- Clean undo/rollback; preserves encapsulation (caretaker can't peek).

## Cons / Trade-offs
- Full snapshots can be memory-heavy for large state/long histories.

## Common Pitfalls (what interviewers probe)
- Confusing it with plain JSON serialization — the point is that the caretaker can't read the snapshot's internals.
- Storing full snapshots when diffs (Command-based reverse ops) would be far cheaper.

## Spring Boot / Framework Sightings
- Saga compensating-state snapshots; workflow-engine checkpoints; editor/form undo stacks.

## Files in this folder
- **`MementoDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`MementoSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** Memento vs just serializing to JSON?
>
> **A:** The pattern's essence is encapsulation: the Memento is opaque to the Caretaker that stores it — only the Originator can read and apply it. Serializing to JSON exposes the internals to anyone. In practice, production undo often stores diffs (reverse commands) instead of full snapshots to save memory, blending Memento with Command.


## Related Patterns
Command, Prototype, State

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
