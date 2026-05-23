package org.rishudesign.com.practice.ParkingLot;

public class ParkingLot {
    private ParkingManagerFactory factory;

    public ParkingLot(ParkingManagerFactory factory){
        this.factory = factory;
    }

    public ParkingSpot parkVechile(Vechile v){
        ParkingSpotManager manager= factory.getManager(v.getVechileType());
        ParkingSpot spot= manager.parkVechile(v);
        return spot;
    }

    public void exit(ParkingSpot spot,Vechile v){
        ParkingSpotManager manager = factory.getManager(v.getVechileType());
        manager.freeSpot(spot);
    }
}
