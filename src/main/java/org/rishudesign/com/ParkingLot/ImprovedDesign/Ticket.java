package org.rishudesign.com.ParkingLot.ImprovedDesign;

import java.util.UUID;

public class Ticket {
    private final String id;
    private final long entryTime;
    private final Vechile vechile;
    private final ParkingSpot spot;

    public Ticket(Vechile vehicle, ParkingSpot spot) {
        this.id = UUID.randomUUID().toString();
        this.entryTime = System.currentTimeMillis();
        this.vechile = vehicle;
        this.spot = spot;
    }

    public long getEntryTime() { return entryTime; }
    public Vechile getVechile() { return vechile; }
    public ParkingSpot getSpot() { return spot; }
}
