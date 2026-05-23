package org.rishudesign.com.practice.ParkingLot;

import java.util.List;

public class TwoWheelerParkingSpotManager extends ParkingSpotManager{

    public TwoWheelerParkingSpotManager(List<ParkingSpot> spots,ParkingSpotStrategy strategy) {
        super(spots,strategy);
    }
}
