# Quick Reference Card - ExitGate & LLD Problem Solving

## ExitGate at a Glance

```
┌──────────────────────────────────────────────────────────┐
│                     EXITGATE CLASS                        │
├──────────────────────────────────────────────────────────┤
│                                                            │
│  Constructor:                                             │
│  ├─ ExitGate(PricingStrategy pricingStrategy)            │
│                                                            │
│  Methods:                                                 │
│  ├─ double calculateCost(Ticket ticket)                 │
│  │  └─ Returns: Parking fee based on duration & type    │
│  │                                                        │
│  ├─ double processExit(Ticket ticket)                   │
│  │  └─ Returns: Cost + frees parking spot              │
│  │                                                        │
│  └─ String generateReceipt(Ticket ticket, double cost) │
│     └─ Returns: Formatted receipt for customer         │
│                                                            │
└──────────────────────────────────────────────────────────┘
```

---

## ExitGate Flow Diagram

```
        VEHICLE EXITS
            │
            ▼
    ┌──────────────────┐
    │  Fetch Entry     │
    │  Time from       │
    │  Ticket          │
    └────────┬─────────┘
             │
             ▼
    ┌──────────────────┐
    │  Get Current     │
    │  Exit Time       │
    └────────┬─────────┘
             │
             ▼
    ┌──────────────────┐
    │  Calculate       │
    │  Duration        │
    │  (min)           │
    └────────┬─────────┘
             │
             ▼
    ┌──────────────────┐
    │  Determine       │
    │  DurationType    │
    │  HOURLY/MINUTE   │
    └────────┬─────────┘
             │
             ▼
    ┌──────────────────────────────────────┐
    │  Call                                │
    │  PricingStrategy.calculatePrice()    │
    │  Returns: Cost                       │
    └────────┬─────────────────────────────┘
             │
             ├─► Remove Vehicle from Spot
             │
             ├─► Generate Receipt
             │
             └─► Return Cost
                    │
                    ▼
                Customer Pays
```

---

## Cost Calculation Examples

### Scenario 1: 2-Wheeler Parked for 30 Minutes
```
Entry Time:     10:00 AM
Exit Time:      10:30 AM
Duration:       30 minutes (< 60)
DurationType:   MINUTE
Vehicle:        TWO_WHEELER (bike)
Pricing:        $10 per minute

Cost = 30 × $10 = $300
```

### Scenario 2: 4-Wheeler Parked for 1.5 Hours
```
Entry Time:     10:00 AM
Exit Time:      11:30 AM
Duration:       90 minutes (≥ 60)
DurationType:   HOURLY
Duration:       2 hours (ceil(90/60))
Vehicle:        FOUR_WHEELER (car)
Pricing:        $20 per hour

Cost = 2 × $20 = $40
```

---

## LLD Problem-Solving Framework

```
┌─────────────────────────────────────────────────────────┐
│                    6-PHASE FRAMEWORK                      │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  PHASE 1: UNDERSTANDING (5-10 min)                      │
│  ├─ What is the responsibility?                         │
│  ├─ What are inputs/outputs?                            │
│  ├─ What are business rules?                            │
│  └─ What are failure scenarios?                         │
│                                                           │
│  PHASE 2: DATA GATHERING (5 min)                        │
│  ├─ What data do I need?                                │
│  ├─ Where do I get it?                                  │
│  └─ How do I access it?                                 │
│                                                           │
│  PHASE 3: CODE EXPLORATION (5-10 min)                   │
│  ├─ Look at related classes                             │
│  ├─ Identify available methods                          │
│  └─ Study design patterns used                          │
│                                                           │
│  PHASE 4: ALGORITHM DESIGN (5-10 min)                   │
│  ├─ Break into clear steps                              │
│  ├─ Identify decision points                            │
│  └─ Plan error handling                                 │
│                                                           │
│  PHASE 5: IMPLEMENTATION (10-15 min)                    │
│  ├─ Code the solution                                   │
│  ├─ Add documentation                                   │
│  └─ Handle edge cases                                   │
│                                                           │
│  PHASE 6: VERIFICATION (5 min)                          │
│  ├─ Check against requirements                          │
│  ├─ Verify code quality                                 │
│  └─ Test integration                                    │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

---

## Common LLD Design Patterns

### 1. Strategy Pattern
```
Used in: PricingStrategy (different pricing rules)

✓ When: Multiple ways to do same thing
✓ How: Interface + Multiple implementations
✓ Benefit: Easy to add new strategies

  PricingStrategy (Interface)
      ▲         ▲         ▲
      │         │         │
   HourlyPrice FlatRate  PeakHourPrice
```

### 2. Factory Pattern
```
Used in: ParkingSpaceFactory (create parking spots)

✓ When: Creating different types of objects
✓ How: Separate factory class creates objects
✓ Benefit: Centralized object creation

  Factory
    ├─ createTwoWheelerSpot()
    ├─ createFourWheelerSpot()
    └─ createCarSpot()
```

### 3. Dependency Injection
```
Used in: ExitGate receives PricingStrategy

✓ When: Component depends on another
✓ How: Pass dependency as constructor parameter
✓ Benefit: Loose coupling, easy testing

  ❌ Bad:  ExitGate creates PricingStrategy
  ✅ Good: ExitGate receives PricingStrategy
```

---

## When You Get Stuck - Quick Solutions

| Problem | Solution | Reference |
|---------|----------|-----------|
| Don't know where to start | Use 6-phase framework | Phase 1: Understanding |
| Missing data | Check related classes for getters | Phase 3: Code Exploration |
| Too complex | Break into smaller methods | Phase 4: Algorithm Design |
| Don't know HOW | Look at similar code in project | Phase 3: Code Exploration |
| Code feels wrong | Check SOLID principles | LLD_PROBLEM_SOLVING_GUIDE |
| Need guidance | Read ExitGate explanation | EXITGATE_DETAILED_EXPLANATION |

---

## SOLID Principles Checklist

```
S - Single Responsibility
  ✓ ExitGate: Only handles exit operations
  ✓ PricingStrategy: Only handles pricing
  ✓ ParkingSpot: Only handles spot state

O - Open/Closed
  ✓ Open for extension: New pricing strategies
  ✓ Closed for modification: ExitGate doesn't need changes

L - Liskov Substitution
  ✓ Any PricingStrategy can replace another

I - Interface Segregation
  ✓ PricingStrategy: Minimal interface
  ✓ No unnecessary methods

D - Dependency Inversion
  ✓ ExitGate depends on PricingStrategy (abstraction)
  ✓ Not on HourlyPrice (concrete)
```

---

## ExitGate Integration Points

```
EntryGate              ExitGate              Payment
    │                    │                      │
    ├─ Create Ticket ─┐  │                      │
    │                 │  │                      │
    │                 └─► calculateCost ──────┐ │
    │                 │                       │ │
    │                 │                       ▼ │
    │                 │                    Process
    │                 │                    Payment
    │                 │                       │
    │                 ├─ processExit ◄──────┘ │
    │                 │   (free spot)         │
    │                 │                       │
    │                 └─ generateReceipt      │
    │                   (show customer)       │
    │                                        ▼
    │                                    Exit Complete
    │
    └─ Update Entry Gate Counter
```

---

## Code Quality Checklist

Before submitting, ensure:

```
Functionality:
  ☐ Does it do what it's supposed to?
  ☐ Are all cases handled?
  ☐ Do calculations work?

Design:
  ☐ Follows existing patterns?
  ☐ Uses SOLID principles?
  ☐ Depends on abstractions?

Error Handling:
  ☐ Null checks done?
  ☐ Edge cases handled?
  ☐ Invalid input considered?

Documentation:
  ☐ Methods have JavaDoc?
  ☐ Complex logic commented?
  ☐ Clear variable names?

Integration:
  ☐ Works with other components?
  ☐ Correct method signatures?
  ☐ Consistent with codebase?
```

---

## File Locations Reference

```
Main Implementation:
├─ ExitGate.java
└─ Ticket.java (updated)

Documentation:
├─ IMPLEMENTATION_SUMMARY.md ← START HERE
├─ LLD_PROBLEM_SOLVING_GUIDE.md (principles & mindset)
├─ LLD_STEP_BY_STEP_FRAMEWORK.md (how-to guide)
├─ EXITGATE_DETAILED_EXPLANATION.md (deep dive)
└─ QUICK_REFERENCE_CARD.md (this file)

Example:
└─ ExitGateExample.java
```

---

## Metrics to Evaluate Your LLD

```
Scalability:
  Can it handle 1000 parking lots? 100k vehicles?
  → Yes, if designed properly

Maintainability:
  Can you change pricing without touching ExitGate?
  → Yes, using PricingStrategy

Extensibility:
  Can you add new features easily?
  → Yes, add new methods or strategies

Robustness:
  Does it handle errors gracefully?
  → Yes, with proper null checks

Testability:
  Can you test it independently?
  → Yes, with dependency injection
```

---

## Remember

```
┌─────────────────────────────────────────────────────┐
│                                                     │
│  "Good LLD = Good Understanding + Planning"        │
│             + "Following Patterns"                  │
│             + "Incremental Implementation"          │
│                                                     │
│  NOT = "Brilliant Ideas" or "Quick Coding"         │
│                                                     │
│  Spend TIME on:                                     │
│  1. Understanding the problem                      │
│  2. Exploring similar code                         │
│  3. Planning your approach                         │
│  4. Building incrementally                         │
│  5. Verifying your solution                        │
│                                                     │
│  DON'T spend time on:                              │
│  1. Guessing and coding randomly                   │
│  2. Ignoring existing patterns                     │
│  3. Building everything at once                    │
│  4. Hardcoding values                              │
│  5. Ignoring edge cases                            │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## Next Steps

1. **Read**: `IMPLEMENTATION_SUMMARY.md` (overview)
2. **Understand**: `EXITGATE_DETAILED_EXPLANATION.md` (how it works)
3. **Learn**: `LLD_STEP_BY_STEP_FRAMEWORK.md` (problem solving)
4. **Reference**: `LLD_PROBLEM_SOLVING_GUIDE.md` (principles)
5. **Practice**: Implement next component using the framework

---

**You're now equipped to solve ANY LLD problem! 🚀**

