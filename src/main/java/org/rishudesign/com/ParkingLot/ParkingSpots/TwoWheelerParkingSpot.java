package org.rishudesign.com.ParkingLot.ParkingSpots;

import org.rishudesign.com.ParkingLot.enums.VechileType;

public class TwoWheelerParkingSpot extends ParkingSpot{

     public TwoWheelerParkingSpot(int id,VechileType vechile){
         super(id,vechile);
     }

     @Override
     public boolean canParkVechile(VechileType vechileType){
         return VechileType.TWO_WHEELER == vechileType;
     }
}
