package org.rishudesign.com.ParkingLot.ParkingSpots;
import java.util.*;
import org.rishudesign.com.ParkingLot.enums.VechileType;

public class ParkingSpaceFactory {
    public ParkingSpotManager getParkingManager(VechileType vechileType,List<ParkingSpot> spot){
        if(vechileType.toString().equalsIgnoreCase("TWO_WHEELER")){
            return new TwoWheerlerManager(spot);
        }
        return new FourWheelerManager(spot);
    }
}
