package org.rishudesign.com.practice.ParkingLot;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NearesParkingSpotStrategy implements ParkingSpotStrategy{

    @Override
    public ParkingSpot findParkingSpot(Queue<ParkingSpot>freeSpots){
        if(!freeSpots.isEmpty())
            return freeSpots.poll();

        return null;  // ✅ Return null instead of throwing exception
    }
}
