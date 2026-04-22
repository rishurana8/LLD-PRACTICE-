package org.rishudesign.com.ParkingLot.OtherClasses;

import org.rishudesign.com.ParkingLot.ParkingSpots.ParkingSpot;
import org.rishudesign.com.ParkingLot.ParkingSpots.Vechile;

public class Ticket {
    private long entryTime;
    private Vechile vechile;
    private ParkingSpot spot;

    public Ticket(long entryTime, Vechile vechile, ParkingSpot spot) {
        this.entryTime = entryTime;
        this.vechile = vechile;
        this.spot = spot;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public Vechile getVechile() {
        return vechile;
    }

    public void setVechile(Vechile vechile) {
        this.vechile = vechile;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpot spot) {
        this.spot = spot;
    }
}
