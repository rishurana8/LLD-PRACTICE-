package org.rishudesign.com.ParkingLot.ParkingSpots;

import org.rishudesign.com.ParkingLot.enums.VechileType;

public class FourWheelerParkingSpot extends ParkingSpot{
    public FourWheelerParkingSpot(int id, VechileType vechile){
        super(id,vechile);
    }

    @Override
    public boolean canParkVechile(VechileType vechileType){
        return VechileType.FOUR_WHEELER == vechileType;
    }
}
