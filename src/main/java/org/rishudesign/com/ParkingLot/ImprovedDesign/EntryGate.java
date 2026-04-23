package org.rishudesign.com.ParkingLot.ImprovedDesign;

public class EntryGate {
    private ParkingLot parkingLot;

    public EntryGate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    public Ticket parkVechile(Vechile v){
        ParkingSpot spot = parkingLot.park(v);
        return new Ticket(v,spot);
    }
}
