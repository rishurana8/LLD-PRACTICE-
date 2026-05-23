package org.rishudesign.com.practice.ParkingLot;



import java.util.UUID;

public class Ticket {
    private int  id;
    private long entryTime;
    private Vechile vechile;
    private ParkingSpot spot;

    public Ticket(Vechile vechile,ParkingSpot spot){
        this.id = id;
        this.entryTime = entryTime;
        this.vechile = vechile;
        this.spot = spot;
    }


    public long getEntryTime() { return entryTime; }
    public Vechile getVechile() { return vechile; }
    public ParkingSpot getSpot() { return spot; }
}
