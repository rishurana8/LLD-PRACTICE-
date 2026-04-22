# ExitGate Implementation Explained

## Overview
The `ExitGate` class is responsible for:
1. **Calculating the parking cost** based on duration and vehicle type
2. **Processing vehicle exit** (freeing up the parking spot)
3. **Generating receipts** for the customer

---

## Class Structure

### Constructor
```java
public ExitGate(PricingStrategy pricingStrategy) {
    this.pricingStrategy = pricingStrategy;
}
```
- Takes a `PricingStrategy` as a dependency (Dependency Injection)
- Allows flexibility to change pricing logic without modifying ExitGate
- Uses Strategy Pattern for extensibility

---

## Methods Explained

### 1. `calculateCost(Ticket ticket)`

**Purpose**: Calculate the parking fee

**How it works**:
```
Step 1: Get entry time from the ticket
        long entryTime = ticket.getEntryTime();

Step 2: Get current exit time
        long exitTime = System.currentTimeMillis();

Step 3: Calculate duration in minutes
        long durationInMinutes = (exitTime - entryTime) / (1000 * 60);
        
Step 4: Determine billing type (HOURLY vs MINUTE)
        - If duration < 60 minutes → charge per minute (MINUTE)
        - If duration ≥ 60 minutes → charge per hour (HOURLY)

Step 5: Convert duration to appropriate unit
        - For HOURLY: Convert to hours (with ceiling)
        - For MINUTE: Keep as minutes

Step 6: Get vehicle type and call pricing strategy
        double cost = pricingStrategy.calculatePrice(vehicleType, durationType, duration);

Step 7: Return cost
        return cost;
```

**Example**:
- Vehicle enters at 10:00 AM
- Vehicle exits at 10:45 AM (45 minutes)
- Duration: 45 minutes → DurationType.MINUTE
- If bike: 45 × 10 = $450
- If car: 45 × 20 = $900

**Business Logic**:
```
Bike (2-wheeler):
  - $10 per minute OR $10 × 60 = $600 per hour

Car (4-wheeler):
  - $20 per minute OR $20 × 60 = $1200 per hour
```

---

### 2. `processExit(Ticket ticket)`

**Purpose**: Complete the exit process

**How it works**:
```
Step 1: Calculate the cost
        double cost = calculateCost(ticket);

Step 2: Remove vehicle from parking spot
        ParkingSpot spot = ticket.getSpot();
        if (spot != null) {
            spot.removeVechile(ticket.getVechile());
        }
        
        This frees up the spot for other vehicles

Step 3: Return the cost
        return cost;
```

**Why this is important**:
- Calculates the cost
- Frees up the parking spot
- Prevents other vehicles from parking in an occupied spot

---

### 3. `generateReceipt(Ticket ticket, double cost)`

**Purpose**: Generate a receipt for the customer

**How it works**:
```
Creates a formatted receipt with:
- Vehicle number (license plate)
- Vehicle type (bike or car)
- Entry time (when vehicle entered)
- Exit time (when vehicle exited)
- Parking spot ID (where it was parked)
- Cost (amount to be paid)
```

**Example Output**:
```
========== PARKING RECEIPT ==========
Vehicle Number: AP12AB5678
Vehicle Type: TWO_WHEELER
Entry Time: Sat Apr 21 10:00:00 IST 2026
Exit Time: Sat Apr 21 10:45:00 IST 2026
Parking Spot ID: ParkingSpot@1234567
Cost: $450.00
====================================
```

---

## Design Patterns Used

### 1. **Dependency Injection**
```java
public ExitGate(PricingStrategy pricingStrategy) {
    this.pricingStrategy = pricingStrategy;
}
```
- ExitGate doesn't create its own pricing strategy
- Receives it from outside (flexible, testable)

### 2. **Strategy Pattern**
```java
double cost = pricingStrategy.calculatePrice(vehicleType, durationType, duration);
```
- Different pricing strategies can be plugged in
- Current: HourlyPrice
- Future: FlatRate, PeakHourPrice, etc.

### 3. **Separation of Concerns**
- ExitGate handles: cost calculation & exit processing
- PricingStrategy handles: pricing rules
- ParkingSpot handles: parking state management

---

## Integration with Other Classes

```
┌─────────────────┐
│  EntryGate      │
│  (creates ticket│ ──┐
│   when entering)│   │
└─────────────────┘   │
                      │
                      ▼
            ┌──────────────────┐
            │    Ticket        │
            │ (holds all info) │
            └──────────────────┘
                      │
                      │ passed to
                      ▼
            ┌──────────────────┐
            │    ExitGate      │ ◄─── Uses PricingStrategy
            │ (processes exit) │      (calculates cost)
            └──────────────────┘
                      │
                      ├─► Updates ParkingSpot
                      │   (removes vehicle, frees spot)
                      │
                      └─► Returns Cost + Receipt
                          to customer
```

---

## Edge Cases Handled

### 1. **Null Spot Check**
```java
if (spot != null) {
    spot.removeVechile(ticket.getVechile());
}
```
- Prevents NullPointerException if spot is null

### 2. **Duration Calculation**
```java
long durationInMinutes = (exitTime - entryTime) / (1000 * 60);
DurationType durationType = durationInMinutes < 60 
                            ? DurationType.MINUTE 
                            : DurationType.HOURLY;
```
- Handles both short stays (minutes) and long stays (hours)

### 3. **Ceiling for Hourly Charges**
```java
Math.ceil(durationInMinutes / 60.0)
```
- If parked for 65 minutes, charged for 2 hours (not 1.08)
- Fair to the parking lot operator

---

## How to Use ExitGate

```java
// 1. Create a pricing strategy
PricingStrategy pricingStrategy = new HourlyPrice();

// 2. Create an exit gate
ExitGate exitGate = new ExitGate(pricingStrategy);

// 3. When vehicle exits, call processExit
double cost = exitGate.processExit(ticket);

// 4. Generate and show receipt
String receipt = exitGate.generateReceipt(ticket, cost);
System.out.println(receipt);
```

---

## Future Enhancements

1. **Payment Processing**
   - Add payment gateway integration
   - Handle multiple payment methods

2. **More Pricing Strategies**
   - PeakHourPrice (higher rates during rush hours)
   - FlatRatePrice (fixed amount regardless of duration)
   - MembershipPrice (discounts for members)

3. **Receipt Management**
   - Save receipts to database
   - Email receipt to customer
   - Generate reports for management

4. **Validation**
   - Check if ticket is valid
   - Prevent re-exit with same ticket
   - Prevent exit before sufficient payment

5. **Extensibility**
   ```java
   public interface ExitGate {
       double calculateCost(Ticket ticket);
       double processExit(Ticket ticket);
       String generateReceipt(Ticket ticket, double cost);
       void sendReceiptToEmail(String email, String receipt);
   }
   ```

---

## Key Takeaways

✅ **ExitGate is single-responsibility**: Only handles exit operations
✅ **Uses composition**: Depends on PricingStrategy, not hard-coded logic
✅ **Extensible design**: New pricing strategies can be added easily
✅ **Handles edge cases**: Null checks, proper time calculations
✅ **Clear separation**: Each method has a single, clear purpose
✅ **Real-world logic**: Ceiling charges, receipt generation

---

## Common Mistakes to Avoid

❌ **Don't**: Put pricing logic directly in ExitGate
✅ **Do**: Delegate to PricingStrategy

❌ **Don't**: Hardcode vehicle rates
✅ **Do**: Use enums and strategy pattern

❌ **Don't**: Forget to free up parking spots
✅ **Do**: Call spot.removeVechile()

❌ **Don't**: Ignore time zone differences
✅ **Do**: Consider using proper date/time libraries (LocalDateTime)

---

This is a well-designed, maintainable solution for the ExitGate component!

