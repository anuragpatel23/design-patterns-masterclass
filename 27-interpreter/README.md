# 27. Interpreter Pattern

**Category:** 🟠 Behavioral

> Define a grammar for a simple language and an interpreter that evaluates sentences in it.

---

## Intent
Given a language, define a representation for its grammar along with an interpreter that uses the representation to evaluate sentences — useful for simple, well-defined grammars (rule engines, filters, config expressions).

## Real-World Analogy
A **spam filter's rule language**: `sender contains 'noreply' AND subject contains 'winner'`. Each grammar element (Contains, And, Not) is a small object that knows how to evaluate itself; rules are composed as a tree from these building blocks — no redeploy per new rule.

## When to Use
- You have a small DSL for business rules that change often (discount eligibility, validation).
- You must parse/evaluate simple expression languages (search syntax, feature-flag targeting).
- The grammar is simple and stable.

## Structure
```
Expression.interpret(context)
Terminal (leaf) + NonTerminal (And/Or/Not composing sub-expressions)
```

## Participants
- **AbstractExpression** — `interpret(context)`.
- **TerminalExpression** — vocabulary leaves.
- **NonTerminalExpression** — grammar combinators (And/Or/Not).

## Pros
- Business rules become data/config, composable at runtime; no redeploy per rule.

## Cons / Trade-offs
- Only viable for SIMPLE grammars; complex ones explode into many classes.

## Common Pitfalls (what interviewers probe)
- Hand-rolling a full query language — reach for ANTLR or a rules engine (Drools) instead.
- Confusing it with Strategy/Composite (it uses Composite structurally).

## Spring Boot / Framework Sightings
- Spring Expression Language (SpEL), regex engines, SQL parsers are larger relatives.
- At scale, Drools/rule engines replace hand-rolled interpreters.

## Files in this folder
- **`InterpreterDemo.java`** — pure-Java, self-contained, runnable (`main` included).
- **`InterpreterSpringExample.java`** — idiomatic Spring Boot usage.

## Interview Soundbites
> **Q:** "Let business users change discount rules without a redeploy" — how?
>
> **A:** Model the rules as an expression tree the Interpreter evaluates, with the tree built from a database-stored definition. Terminal expressions are conditions (min orders, region); non-terminals are AND/OR/NOT. For anything beyond simple grammars, use a real rules engine like Drools rather than hand-rolling.


## Related Patterns
Composite, Visitor, Specification

---
*Part of the Design Patterns Interview Prep — golden source. See the root `README.md` for the full index.*
