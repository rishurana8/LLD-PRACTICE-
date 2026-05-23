package org.rishudesign.com.practice.ParkingLot;

import java.util.List;

public class FourWheelerParkingSpotManager extends ParkingSpotManager{

    public FourWheelerParkingSpotManager(List<ParkingSpot> spots,ParkingSpotStrategy strategy) {
        super(spots,strategy);
    }
}
