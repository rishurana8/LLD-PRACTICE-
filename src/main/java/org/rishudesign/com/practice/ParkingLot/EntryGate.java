package org.rishudesign.com.practice.ParkingLot;

public class EntryGate {
    private ParkingLot parkingLot;

    public EntryGate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    public Ticket parkVechile(Vechile v){
        ParkingSpot spot = parkingLot.parkVechile(v);
        return new Ticket(v,spot);
    }
}
