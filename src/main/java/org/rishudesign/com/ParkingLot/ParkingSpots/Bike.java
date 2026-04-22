package org.rishudesign.com.ParkingLot.ParkingSpots;

import org.rishudesign.com.ParkingLot.enums.VechileType;

public class Bike extends Vechile{
    public Bike(String BikeNo){
        super(BikeNo, VechileType.TWO_WHEELER);
    }
}
