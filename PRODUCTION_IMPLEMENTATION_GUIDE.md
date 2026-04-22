# Parking Lot LLD - Production-Ready Implementation Guide

## Issue #1: Thread Safety Fix ⛔

### Current Code (UNSAFE):
```java
public ParkingSpot parkVechile(Vechile vechile){
    ParkingSpot spot = findAvailabeSpot();
    if(spot != null) {
        spot.parkVechile(vechile);
    }
    return spot;
}
```

### Production-Ready Code:
```java
import java.util.concurrent.*;

public class ParkingSpotManager {
    private final List<ParkingSpot> parkingSpots;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ParkingSpotManager(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = Collections.synchronizedList(parkingSpots);
    }

    public ParkingSpot parkVechile(Vechile vechile) {
        lock.writeLock().lock();
        try {
            ParkingSpot spot = findAvailableSpot();
            if (spot != null) {
                spot.parkVechile(vechile);
                System.out.println("Vehicle Parked at spot: " + spot.getId());
            } else {
                throw new IllegalStateException("No available parking spot");
            }
            return spot;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private ParkingSpot findAvailableSpot() {
        return parkingSpots.stream()
            .filter(ParkingSpot::isAvailable)
            .findFirst()
            .orElse(null);
    }

    public void removeVechile(ParkingSpot spot, Vechile vechile) {
        lock.writeLock().lock();
        try {
            if (!spot.isAvailable() && spot.getVechile().equals(vechile)) {
                spot.removeVechile(vechile);
                System.out.println("Vehicle removed");
            } else {
                throw new IllegalStateException("Invalid removal request");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

---

## Issue #2: Fix Hardcoded Entry Time Bug

### Current Code (WRONG):
```java
public Ticket GenerateTicket(){
    Ticket ticket = new Ticket(11, vechile, spot);  // ❌ Hardcoded!
    return ticket;
}
```

### Fixed Code:
```java
public Ticket generateTicket() {
    long entryTime = System.currentTimeMillis();
    Ticket ticket = new Ticket(entryTime, vechile, spot);
    System.out.println("Ticket generated at: " + new Date(entryTime));
    return ticket;
}
```

---

## Issue #3: Remove Redundant Manager Classes

### Current (REDUNDANT):
```java
public class FourWheelerManager extends ParkingSpotManager{
    public FourWheelerManager(List<ParkingSpot> spots){
        super(spots);  // No override - just calls parent
    }
}

public class TwoWheerlerManager extends ParkingSpotManager{
    public TwoWheerlerManager(List<ParkingSpot> spots){
        super(spots);  // No override - just calls parent
    }
}
```

### Better Design:
```java
// Option 1: Use a factory and single manager
public class ParkingSpotManager {
    private final List<ParkingSpot> parkingSpots;
    private final VehicleType vehicleType;
    
    public ParkingSpotManager(List<ParkingSpot> spots, VehicleType type) {
        this.parkingSpots = spots;
        this.vehicleType = type;
    }
}

// Option 2: Remove manager specialization entirely
public class ParkingSpaceFactory {
    public ParkingSpotManager getParkingManager(
        VehicleType vehicleType, 
        List<ParkingSpot> spots) {
        return new ParkingSpotManager(spots);  // All use same manager
    }
}
```

---

## Issue #4: Add Service Layer (Production Architecture)

### New Service Class:
```java
import java.util.logging.Logger;

public class ParkingService {
    private static final Logger logger = Logger.getLogger(ParkingService.class.getName());
    
    private final ParkingSpaceFactory factory;
    private final PricingService pricingService;
    private final TicketRepository ticketRepository;

    public ParkingService(
        ParkingSpaceFactory factory,
        PricingService pricingService,
        TicketRepository ticketRepository) {
        this.factory = factory;
        this.pricingService = pricingService;
        this.ticketRepository = ticketRepository;
    }

    public Ticket parkVehicle(Vehicle vehicle, List<ParkingSpot> spots) {
        try {
            ParkingSpotManager manager = factory.getParkingManager(
                vehicle.getVehicleType(), 
                spots
            );
            ParkingSpot spot = manager.parkVehicle(vehicle);
            
            Ticket ticket = new Ticket(
                System.currentTimeMillis(),
                vehicle,
                spot
            );
            
            ticketRepository.save(ticket);
            logger.info("Vehicle " + vehicle.getVehicleNo() + " parked successfully");
            return ticket;
        } catch (Exception e) {
            logger.severe("Error parking vehicle: " + e.getMessage());
            throw new ParkingException("Failed to park vehicle", e);
        }
    }

    public ParkingReceipt exitVehicle(Ticket ticket, PaymentInfo paymentInfo) {
        try {
            ParkingSpot spot = ticket.getSpot();
            double cost = pricingService.calculateCost(ticket);
            
            // Process payment
            if (!processPayment(paymentInfo, cost)) {
                throw new PaymentException("Payment processing failed");
            }
            
            // Remove vehicle
            spot.removeVehicle(ticket.getVehicle());
            
            ParkingReceipt receipt = new ParkingReceipt(ticket, cost);
            return receipt;
        } catch (Exception e) {
            logger.severe("Error processing exit: " + e.getMessage());
            throw new ParkingException("Failed to process exit", e);
        }
    }

    private boolean processPayment(PaymentInfo paymentInfo, double amount) {
        // Integration with payment gateway
        logger.info("Processing payment of $" + amount);
        return true;  // Simplified
    }

    public List<ParkingSpot> getAvailableSpots(VehicleType vehicleType) {
        return ticketRepository.findAvailableSpots(vehicleType);
    }
}
```

---

## Issue #5: Custom Exception Hierarchy

### Add Custom Exceptions:
```java
public class ParkingException extends RuntimeException {
    public ParkingException(String message) {
        super(message);
    }

    public ParkingException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class NoSpaceAvailableException extends ParkingException {
    public NoSpaceAvailableException(String vehicleType) {
        super("No parking space available for " + vehicleType);
    }
}

public class PaymentException extends ParkingException {
    public PaymentException(String message) {
        super(message);
    }
}

public class InvalidVehicleException extends ParkingException {
    public InvalidVehicleException(String vehicleNo) {
        super("Invalid vehicle: " + vehicleNo);
    }
}
```

---

## Issue #6: Repository/DAO Layer (Database Access)

### Ticket Repository:
```java
import java.util.List;

public interface TicketRepository {
    void save(Ticket ticket);
    Ticket findById(String ticketId);
    List<Ticket> findActiveTickets();
    void update(Ticket ticket);
    void delete(String ticketId);
}

public class TicketRepositoryImpl implements TicketRepository {
    // This would connect to actual database
    private final Map<String, Ticket> tickets = new ConcurrentHashMap<>();

    @Override
    public void save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        // Database INSERT
    }

    @Override
    public Ticket findById(String ticketId) {
        return tickets.get(ticketId);
        // Database SELECT
    }

    @Override
    public List<Ticket> findActiveTickets() {
        return tickets.values().stream()
            .filter(t -> t.getExitTime() == null)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        // Database UPDATE
    }

    @Override
    public void delete(String ticketId) {
        tickets.remove(ticketId);
        // Database DELETE
    }
}
```

---

## Issue #7: Enhanced Pricing Service

### Better Pricing Strategy:
```java
public interface PricingStrategy {
    double calculatePrice(Vehicle vehicle, long durationInMinutes);
}

public class HourlyPricingStrategy implements PricingStrategy {
    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
        VehicleType.TWO_WHEELER, 10.0,
        VehicleType.FOUR_WHEELER, 20.0
    );

    @Override
    public double calculatePrice(Vehicle vehicle, long durationInMinutes) {
        double hourlyRate = HOURLY_RATES.getOrDefault(
            vehicle.getVehicleType(), 
            15.0
        );
        
        long hours = Math.max(1, (durationInMinutes + 59) / 60);  // Round up
        return hours * hourlyRate;
    }
}

public class FixedPlusHourlyStrategy implements PricingStrategy {
    private static final double ENTRY_FEE = 5.0;
    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
        VehicleType.TWO_WHEELER, 8.0,
        VehicleType.FOUR_WHEELER, 18.0
    );

    @Override
    public double calculatePrice(Vehicle vehicle, long durationInMinutes) {
        double hourlyRate = HOURLY_RATES.getOrDefault(
            vehicle.getVehicleType(), 
            12.0
        );
        
        long hours = Math.max(1, (durationInMinutes + 59) / 60);
        return ENTRY_FEE + (hours * hourlyRate);
    }
}
```

---

## Issue #8: Improved Models with IDs and Timestamps

### Enhanced Ticket Class:
```java
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private double cost;
    private TicketStatus status;

    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = UUID.randomUUID().toString();
        this.entryTime = LocalDateTime.now();
        this.vehicle = vehicle;
        this.spot = spot;
        this.status = TicketStatus.ACTIVE;
    }

    public void completeTicket(double cost) {
        this.exitTime = LocalDateTime.now();
        this.cost = cost;
        this.status = TicketStatus.COMPLETED;
    }

    // Getters
    public String getTicketId() { return ticketId; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public double getCost() { return cost; }
    public TicketStatus getStatus() { return status; }
}

public enum TicketStatus {
    ACTIVE, COMPLETED, CANCELLED
}
```

---

## Issue #9: Add Logging (NOT System.out.println)

### Proper Logging Example:
```java
import java.util.logging.*;

public class EntryGate {
    private static final Logger logger = Logger.getLogger(EntryGate.class.getName());
    
    private final ParkingSpaceFactory factory;
    private final Vehicle vehicle;
    private ParkingSpot spot;

    public EntryGate(Vehicle vehicle) {
        this.factory = new ParkingSpaceFactory();
        this.vehicle = vehicle;
        logger.info("Entry gate initialized for vehicle: " + vehicle.getVehicleNo());
    }

    public Ticket parkVehicle(List<ParkingSpot> spots) {
        try {
            ParkingSpotManager manager = factory.getParkingManager(
                vehicle.getVehicleType(), 
                spots
            );
            spot = manager.parkVehicle(vehicle);
            logger.info("Vehicle " + vehicle.getVehicleNo() + " parked at spot " + spot.getId());
            return generateTicket();
        } catch (NoSpaceAvailableException e) {
            logger.warning("No space available: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            throw new ParkingException("Failed to park vehicle", e);
        }
    }

    private Ticket generateTicket() {
        return new Ticket(vehicle, spot);
    }
}
```

---

## Issue #10: API Layer (REST Endpoints)

### For Production Implementation:
```java
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @POST
    @Path("/entry")
    @Consumes("application/json")
    @Produces("application/json")
    public Response parkVehicle(VehicleParkingRequest request) {
        try {
            Ticket ticket = parkingService.parkVehicle(
                request.getVehicle(),
                request.getSpots()
            );
            return Response.ok(ticket).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }

    @POST
    @Path("/exit")
    @Consumes("application/json")
    @Produces("application/json")
    public Response exitVehicle(VehicleExitRequest request) {
        try {
            Ticket ticket = parkingService.getTicket(request.getTicketId());
            ParkingReceipt receipt = parkingService.exitVehicle(
                ticket, 
                request.getPaymentInfo()
            );
            return Response.ok(receipt).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }

    @GET
    @Path("/available-spots/{vehicleType}")
    @Produces("application/json")
    public Response getAvailableSpots(@PathParam("vehicleType") String vehicleType) {
        List<ParkingSpot> spots = parkingService.getAvailableSpots(
            VehicleType.valueOf(vehicleType)
        );
        return Response.ok(spots).build();
    }
}
```

---

## Summary of Critical Fixes

| Issue | Severity | Fix |
|-------|----------|-----|
| No thread safety | 🔴 CRITICAL | Add ReentrantReadWriteLock |
| Hardcoded entry time | 🔴 CRITICAL | Use System.currentTimeMillis() |
| No persistence | 🔴 CRITICAL | Add Repository layer |
| No service layer | 🔴 CRITICAL | Create ParkingService |
| Redundant managers | 🟠 HIGH | Remove or consolidate |
| Poor error handling | 🟠 HIGH | Add custom exceptions |
| No logging | 🟠 HIGH | Use proper logging framework |
| Naming typos | 🟡 MEDIUM | Fix Vechile→Vehicle |
| No API layer | 🟡 MEDIUM | Add REST endpoints |

---

## Final Checklist for Production

- [ ] All operations are thread-safe
- [ ] Database persistence implemented
- [ ] Proper exception handling with logging
- [ ] Service layer separates business logic
- [ ] Repository pattern for data access
- [ ] API endpoints documented
- [ ] Naming conventions followed
- [ ] No hardcoded values
- [ ] Comprehensive unit tests
- [ ] Performance optimizations for scale

Good luck! 🚀

