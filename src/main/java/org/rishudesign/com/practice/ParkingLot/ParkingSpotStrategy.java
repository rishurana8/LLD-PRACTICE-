package org.rishudesign.com.practice.ParkingLot;

import java.util.Queue;

public interface ParkingSpotStrategy {

    public ParkingSpot findParkingSpot(Queue<ParkingSpot> freeSpots);
}
