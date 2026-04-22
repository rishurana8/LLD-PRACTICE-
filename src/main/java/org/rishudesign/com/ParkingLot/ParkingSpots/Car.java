package org.rishudesign.com.ParkingLot.ParkingSpots;

import org.rishudesign.com.ParkingLot.enums.VechileType;

public class Car extends Vechile{
    public Car(String vechileNo){
        super(vechileNo, VechileType.FOUR_WHEELER);
    }
}
