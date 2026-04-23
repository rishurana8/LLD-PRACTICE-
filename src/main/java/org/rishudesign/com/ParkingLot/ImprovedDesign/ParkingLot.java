package org.rishudesign.com.ParkingLot.ImprovedDesign;
import java.util.*;

public class ParkingLot {
    private Map<VechileType, ParkingSpotManager> managers;

    public ParkingLot(Map<VechileType, ParkingSpotManager>managers){
        this.managers = managers;
    }
    public ParkingSpot park(Vechile v) {
        ParkingSpotManager manager = managers.get(v.getType());

        if (manager == null) {
            throw new RuntimeException("No manager for vehicle type");
        }

        return manager.parkVechile(v);
    }

    public void exit(ParkingSpot spot , Vechile v){
        ParkingSpotManager manager = managers.get(v.getType());
        manager.freeSpot(spot);
    }

}
