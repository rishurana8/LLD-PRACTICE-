# Step-by-Step LLD Problem Solving Framework

## When You Get Stuck - Use This Framework

### Phase 1: Understanding (5-10 minutes)
Before writing ANY code, make sure you understand the problem completely.

#### Questions to Ask:
```
1. What is the main responsibility of this component?
2. What inputs does it receive?
3. What outputs does it produce?
4. Who uses this component?
5. What are the business rules?
6. What are possible failure scenarios?
```

**Example: ExitGate**
```
1. Main responsibility: Calculate parking cost and process exit
2. Inputs: Ticket (contains vehicle, entry time, parking spot)
3. Outputs: Cost (double) and Receipt (String)
4. Users: Payment system, Customer
5. Business rules: Different rates for different vehicle types
6. Failures: Invalid ticket, null spot, zero duration
```

---

### Phase 2: Data Gathering (5 minutes)
Identify all data needed to complete the task.

#### Data Checklist:
```
What data do I need?
- Entry time ✓
- Exit time ✓
- Vehicle type ✓
- Duration ✓
- Parking spot ✓
- Price rates ✓

Where do I get each data point?
- From method parameters ✓
- From other objects ✓
- From external sources ✓
- By calculation ✓
```

**Example: ExitGate Data**
```
Entry time        ← ticket.getEntryTime()
Exit time         ← System.currentTimeMillis()
Vehicle type      ← ticket.getVechile().getVechileType()
Duration          ← (exitTime - entryTime) / (1000 * 60)
Parking spot      ← ticket.getSpot()
Price rates       ← pricingStrategy.calculatePrice(...)
```

---

### Phase 3: Code Exploration (5-10 minutes)
Study existing code to understand patterns and available methods.

#### Exploration Checklist:
```
Related Classes:
- [ ] Ticket class (what fields does it have?)
- [ ] PricingStrategy interface (what methods available?)
- [ ] HourlyPrice implementation (how does it work?)
- [ ] ParkingSpot class (how to free a spot?)
- [ ] Vehicle class (how to get vehicle info?)

Available Methods:
- [ ] ticket.getEntryTime()
- [ ] ticket.getVechile()
- [ ] ticket.getSpot()
- [ ] vehicle.getVechileType()
- [ ] pricingStrategy.calculatePrice(...)
- [ ] spot.removeVechile(...)

Patterns Used:
- [ ] Strategy Pattern (for pricing)
- [ ] Dependency Injection (constructor)
- [ ] Abstract classes (Vehicle, ParkingSpot)
```

---

### Phase 4: Algorithm Design (5-10 minutes)
Break down the logic into clear, manageable steps.

#### Algorithm Template:
```
Method: calculateCost(Ticket ticket)
{
    1. Extract required data
       entryTime = get from ticket
       exitTime = get current time
    
    2. Perform calculations
       duration = calculate from times
       durationType = determine (HOURLY or MINUTE)
    
    3. Get additional info
       vehicleType = get from ticket
    
    4. Call existing services
       cost = pricingStrategy.calculatePrice(...)
    
    5. Return result
       return cost
}

Method: processExit(Ticket ticket)
{
    1. Calculate cost
       cost = calculateCost(ticket)
    
    2. Update state
       spot.removeVechile(...)
    
    3. Return result
       return cost
}

Method: generateReceipt(Ticket ticket, double cost)
{
    1. Create receipt object
       receipt = StringBuilder()
    
    2. Add all relevant information
       append vehicle number
       append vehicle type
       append times
       append cost
    
    3. Format nicely
    
    4. Return formatted string
}
```

---

### Phase 5: Implementation (10-15 minutes)
Code the solution following your algorithm.

#### Implementation Checklist:
```
Code Structure:
- [ ] Correct imports
- [ ] Proper method signatures
- [ ] Return types correct
- [ ] Parameters documented

Logic:
- [ ] All steps from algorithm included
- [ ] Calculations correct
- [ ] Edge cases handled
- [ ] Null checks added
- [ ] Type conversions done

Documentation:
- [ ] Methods have JavaDoc
- [ ] Complex logic has comments
- [ ] Variable names are clear
- [ ] No magic numbers
```

---

### Phase 6: Verification (5 minutes)
Check your code against requirements.

#### Verification Checklist:
```
Functionality:
- [ ] Does it calculate cost correctly?
- [ ] Does it free parking spots?
- [ ] Does it generate receipts?
- [ ] Does it handle different vehicle types?

Code Quality:
- [ ] Follows existing patterns?
- [ ] Uses dependency injection?
- [ ] Handles null values?
- [ ] Has proper error handling?
- [ ] Is it maintainable?

Integration:
- [ ] Works with other components?
- [ ] Calls correct methods?
- [ ] Returns expected types?
- [ ] Doesn't break existing code?
```

---

## Real Example: Solving ExitGate Step by Step

### Step 1: Understanding
```
Q: What is ExitGate responsible for?
A: Calculate parking cost and process vehicle exit

Q: What does it receive?
A: A Ticket object with vehicle, entry time, and parking spot info

Q: What does it return?
A: Cost (double) and receipt (String)
```

### Step 2: Data Gathering
```
I need:
- entryTime (from Ticket)
- exitTime (current time)
- vehicleType (from Ticket → Vehicle)
- duration (calculated)
- pricing rules (from PricingStrategy)
```

### Step 3: Code Exploration
```
Checked:
✓ Ticket has getEntryTime(), getVechile(), getSpot()
✓ Vehicle has getVechileType()
✓ PricingStrategy has calculatePrice(...)
✓ ParkingSpot has removeVechile(...)
✓ DurationType enum has HOURLY and MINUTE
```

### Step 4: Algorithm Design
```
calculateCost(ticket):
  1. Get entryTime from ticket
  2. Get current exitTime
  3. Calculate duration in minutes
  4. Determine type: HOURLY if duration ≥ 60 else MINUTE
  5. Convert duration to hours/minutes
  6. Get vehicleType as string
  7. Call pricingStrategy.calculatePrice(...)
  8. Return cost

processExit(ticket):
  1. Calculate cost
  2. Get parking spot from ticket
  3. If spot not null, remove vehicle
  4. Return cost

generateReceipt(ticket, cost):
  1. Create StringBuilder
  2. Add vehicle number
  3. Add vehicle type
  4. Add entry time
  5. Add exit time
  6. Add spot ID
  7. Add cost
  8. Return formatted string
```

### Step 5: Implementation
```java
public double calculateCost(Ticket ticket) {
    long entryTime = ticket.getEntryTime();
    long exitTime = System.currentTimeMillis();
    long durationInMinutes = (exitTime - entryTime) / (1000 * 60);
    
    DurationType durationType = durationInMinutes < 60 
                                ? DurationType.MINUTE 
                                : DurationType.HOURLY;
    
    int duration = (int) (durationType == DurationType.HOURLY 
                         ? Math.ceil(durationInMinutes / 60.0) 
                         : durationInMinutes);
    
    String vehicleType = ticket.getVechile().getVechileType().toString().toLowerCase();
    
    return pricingStrategy.calculatePrice(vehicleType, durationType, duration);
}
```

### Step 6: Verification
```
✓ Calculates cost based on duration
✓ Handles both hourly and minute pricing
✓ Gets vehicle type correctly
✓ Uses PricingStrategy (not hardcoded)
✓ Returns double as expected
✓ Handles edge case: duration < 60 min
```

---

## Decision Tree: Choosing Implementation Approach

```
When implementing a component:

    ┌─ Is there similar code in the system?
    │  ├─ YES → Copy pattern and adapt
    │  └─ NO  → Follow SOLID principles
    │
    ├─ Does it need flexibility?
    │  ├─ YES → Use Strategy/Factory pattern
    │  └─ NO  → Direct implementation OK
    │
    ├─ Does it depend on other components?
    │  ├─ YES → Use dependency injection
    │  └─ NO  → Can instantiate directly
    │
    ├─ Could it fail?
    │  ├─ YES → Add null checks and validation
    │  └─ NO  → Assume valid input
    │
    └─ Will it need changes?
       ├─ YES → Make it modular, use patterns
       └─ NO  → Simple implementation OK
```

---

## Common Issues and Solutions

### Issue 1: "I don't know where to start"
**Solution**: Answer these questions first:
1. What is the input? (method parameters)
2. What is the output? (return type)
3. What happens in between? (the logic)

### Issue 2: "Too many things to implement"
**Solution**: Break it into smaller methods:
- `calculateCost()`
- `processExit()`
- `generateReceipt()`

### Issue 3: "I don't have the data I need"
**Solution**: Check:
1. Are getters available on related objects?
2. Can I calculate it from available data?
3. Should I ask for it as a parameter?

### Issue 4: "My code is too complex"
**Solution**: 
1. Extract logic into separate methods
2. Use existing design patterns
3. Delegate to other objects
4. Add comments explaining logic

### Issue 5: "I'm duplicating code"
**Solution**:
1. Extract common logic into a method
2. Use inheritance for similar classes
3. Use composition for reusable behavior

---

## Quick Checklist for Every Method

Before submitting your code, ask:

```
Design:
- [ ] Single responsibility?
- [ ] Follows existing patterns?
- [ ] Uses dependency injection?
- [ ] Returns correct type?

Implementation:
- [ ] All cases handled?
- [ ] Null checks done?
- [ ] Edge cases covered?
- [ ] Magic numbers removed?
- [ ] Calculations correct?

Documentation:
- [ ] Method has JavaDoc?
- [ ] Complex logic commented?
- [ ] Variable names clear?
- [ ] No dead code?

Integration:
- [ ] Works with other components?
- [ ] Doesn't break existing code?
- [ ] Follows naming conventions?
- [ ] Consistent with codebase style?
```

---

## Practice Exercise

Try implementing these methods using this framework:

1. **Easy**: `getParkingSpotInfo(ParkingSpot spot)` → returns formatted string
2. **Medium**: `findAvailableSpot(List<ParkingSpot> spots, VechileType type)` → returns ParkingSpot
3. **Hard**: `calculateRefund(Ticket ticket, double amountPaid, double cost)` → returns double

Use the framework above for each one!

---

## Remember

When stuck:
1. **Don't panic** - Break it into smaller parts
2. **Read existing code** - Patterns are already there
3. **Ask questions** - What, why, how
4. **Start simple** - You can optimize later
5. **Test incrementally** - Verify each step
6. **Follow patterns** - Don't reinvent

**Good LLD comes from systematic thinking, not brilliant ideas!**
