package org.rishudesign.com.ParkingLot.ParkingSpots;
import org.rishudesign.com.ParkingLot.OtherClasses.Ticket;

import java .util.*;

public class EntryGate {
    private ParkingSpaceFactory factory;
    private Vechile vechile;
    private ParkingSpot spot;

    public EntryGate(Vechile vechile){
        this.factory = new ParkingSpaceFactory();
        this.vechile = vechile;
    }


    public void ParkVechile(List<ParkingSpot> spots){
        ParkingSpotManager manager = factory.getParkingManager(vechile.getVechileType(),spots);
        spot = manager.parkVechile(vechile);
        System.out.println("Your vechile is parked");
    }  // this is not scalable , entry gate is getting spots and it is dependent on some data structure
    // and also on every request manager is being created


    public Ticket GenerateTicket(){
        Ticket ticket = new Ticket(11,vechile,spot);
        return ticket;
    }



}
