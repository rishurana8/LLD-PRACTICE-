package org.rishudesign.com.practice.ParkingLot;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import static org.rishudesign.com.practice.ParkingLot.ParkingStatus.AVAILABLE;
import static org.rishudesign.com.practice.ParkingLot.ParkingStatus.OCCUPIED;

public class ParkingSpotManager {

    private ParkingSpotStrategy strategy ;

     private Queue<ParkingSpot> freeSpots = new ConcurrentLinkedQueue<>();

    private Map<Integer, ParkingStatus> occupied = new ConcurrentHashMap<>();

    private Map<Integer, ReentrantLock>spotLocks = new ConcurrentHashMap<>();

    public ParkingSpotManager(List<ParkingSpot>spots,ParkingSpotStrategy strategy){
        this.strategy= strategy;
        for(ParkingSpot spot: spots) {
            freeSpots.add(spot);
            spotLocks.put(spot.getId(),new ReentrantLock());
            occupied.put(spot.getId(), AVAILABLE);
        }
    }

    public ParkingSpot parkVechile(Vechile v){
       ParkingSpot spot =  strategy.findParkingSpot(freeSpots);

        if (spot == null) {
            System.out.println("No free parking spots available");  // ✅ Print instead of throw
            return null;
        }

        ReentrantLock lock = spotLocks.get(spot.getId());

        lock.lock();
        try {
            if (occupied.get(spot.getId()) == AVAILABLE) {
                occupied.put(spot.getId(), OCCUPIED);
                freeSpots.remove(spot);
                spot.parkVechile(v);
                return spot;
            } else {
                System.out.println("Spot already occupied!");
            }

            return null;
        } finally {
            lock.unlock();
        }

    }

  public void freeSpot(ParkingSpot spot){

  }


}
