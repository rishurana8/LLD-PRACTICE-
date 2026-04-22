# LLD Problem-Solving Guide

## How to Tackle Stuck Situations in LLD Rounds

When you're stuck implementing a component in an LLD (Low-Level Design) problem, follow this systematic approach:

### 1. **Understand the Requirement Thoroughly**
Before writing a single line of code, ensure you understand:
- **What is this component responsible for?**
- **What are the inputs and outputs?**
- **What are the edge cases?**
- **How does it interact with other components?**

**Example from Parking Lot - ExitGate:**
- Responsibility: Calculate parking cost and process vehicle exit
- Inputs: Ticket (contains vehicle, entry time, parking spot)
- Outputs: Cost amount and receipt
- Interactions: Uses PricingStrategy, modifies ParkingSpot

---

### 2. **Identify the Data You Need**
List all the information required to complete the functionality:

```
For ExitGate:
✓ Entry time (from Ticket)
✓ Exit time (current time)
✓ Vehicle type (from Ticket → Vehicle)
✓ Duration of parking (calculated from entry/exit times)
✓ Pricing rules (from PricingStrategy)
✓ Parking spot (to free it up)
```

---

### 3. **Look at Related Classes**
Always examine related classes to understand:
- What data structures exist
- What methods are available
- What design patterns are being used

**Example - What we found:**
- `Ticket` has: entryTime, vehicle, spot
- `PricingStrategy` interface: calculatePrice(vehicleType, durationType, duration)
- `HourlyPrice` implementation: Different rates for bikes vs cars
- `DurationType`: HOURLY or MINUTE

---

### 4. **Break Down into Steps**
Divide the problem into logical steps:

```
ExitGate Algorithm:
1. Get entry time from ticket
2. Get current exit time
3. Calculate duration between entry and exit
4. Determine if charging should be hourly or minute-based
5. Determine vehicle type
6. Call PricingStrategy to calculate cost
7. Remove vehicle from parking spot
8. Generate receipt
9. Return cost
```

---

### 5. **Use Design Patterns**
Identify patterns already in use and apply them:

**In Parking Lot LLD:**
- **Strategy Pattern**: PricingStrategy (different pricing rules)
- **Factory Pattern**: ParkingSpaceFactory (create different parking spot types)
- **Abstract Classes**: ParkingSpot, Vehicle (define common interface)

---

### 6. **Implement Step by Step**
Don't try to implement everything at once. Build incrementally:

```java
// Step 1: Calculate cost
public double calculateCost(Ticket ticket) {
    // ... implementation
}

// Step 2: Process exit
public double processExit(Ticket ticket) {
    // Uses calculateCost + removes vehicle
}

// Step 3: Generate receipt
public String generateReceipt(Ticket ticket, double cost) {
    // ... implementation
}
```

---

### 7. **Consider Edge Cases**
Always think about:
- **Null checks**: What if ticket is null? What if spot is null?
- **Boundary conditions**: What if duration is 0? Very long?
- **Invalid states**: Can we exit before entering?
- **Type mismatches**: Wrong vehicle type?

**Example:**
```java
if (spot != null) {
    spot.removeVechile(ticket.getVechile());
}
```

---

### 8. **Use Comments and Documentation**
Make your code self-explanatory:

```java
/**
 * Calculate parking cost based on entry/exit time and vehicle type
 * @param ticket - Contains vehicle info, entry time, and parking spot
 * @return - Cost of parking
 */
public double calculateCost(Ticket ticket) {
    // ... implementation
}
```

---

### 9. **Test Your Logic**
Before integration, verify:
- Do calculations work correctly?
- Are getters/setters needed?
- Is the flow logical?

---

## Key Principles for LLD Success

### **1. SOLID Principles**
- **S**ingle Responsibility: Each class has one job
- **O**pen/Closed: Open for extension, closed for modification
- **L**iskov Substitution: Subtypes should be substitutable
- **I**nterface Segregation: Many specific interfaces > one general
- **D**ependency Inversion: Depend on abstractions, not implementations

### **2. Use Appropriate Data Structures**
- Lists/Collections for multiple items
- Maps for key-value relationships
- Sets for unique items

### **3. Think About Scalability**
- Can the design handle 1000 parking spots?
- Can it handle different pricing strategies?
- Can it be extended for future features?

### **4. Delegation Over Implementation**
Instead of doing everything yourself, delegate to appropriate objects:

```java
// Good: Using PricingStrategy (delegation)
double cost = pricingStrategy.calculatePrice(vehicleType, durationType, duration);

// Bad: Implementing pricing logic inside ExitGate
// (violates Single Responsibility)
```

---

## When You're Stuck - Debugging Checklist

- [ ] Have I understood the requirement completely?
- [ ] Do I have all necessary data?
- [ ] Have I looked at similar components in the system?
- [ ] Have I identified the right design patterns?
- [ ] Have I broken it into smaller, manageable steps?
- [ ] Are there any edge cases I'm missing?
- [ ] Have I checked related classes for available methods?
- [ ] Is my solution following existing patterns in the codebase?

---

## ExitGate Implementation - Explained

Your `ExitGate` class follows this flow:

```
┌─────────────────┐
│    Ticket       │
│  (entry info)   │
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────┐
│ 1. Extract entry time           │
│ 2. Get current exit time        │
│ 3. Calculate duration           │
└────────┬────────────────────────┘
         │
         ▼
┌─────────────────────────────────┐
│ 4. Determine DurationType       │
│    (HOURLY or MINUTE)           │
└────────┬────────────────────────┘
         │
         ▼
┌─────────────────────────────────┐
│ 5. Get vehicle type             │
│ 6. Call PricingStrategy         │
└────────┬────────────────────────┘
         │
         ▼
┌─────────────────────────────────┐
│ 7. Calculate cost               │
│ 8. Free parking spot            │
│ 9. Generate receipt             │
│ 10. Return cost                 │
└─────────────────┬───────────────┘
                  │
                  ▼
              Cost to Pay
```

---

## Next Steps

For your Parking Lot LLD, consider:

1. **Complete the Vehiche class**: Add constructors and initialize fields
2. **Implement EntryGate fully**: 
   - Create ticket when vehicle enters
   - Store ticket for exit
3. **Add ParkingLotController**: Orchestrate EntryGate and ExitGate
4. **Handle payment**: Add payment processing
5. **Handle reports**: Vehicles in lot, revenue, availability

---

## Common LLD Mistakes to Avoid

1. **❌ Tight Coupling**: Don't hardcode values, use dependency injection
2. **❌ God Classes**: Don't make one class responsible for everything
3. **❌ Missing null checks**: Always validate inputs
4. **❌ Ignoring edge cases**: Think about boundaries
5. **❌ Poor naming**: Use clear, descriptive names
6. **❌ No separation of concerns**: Each class should have one reason to change
7. **❌ Ignoring design patterns**: Use established patterns, don't reinvent

---

Happy coding! Remember: **Good LLD = Good Communication + Clear Thinking + Incremental Implementation**
