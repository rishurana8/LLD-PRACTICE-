package org.rishudesign.com.ParkingLot.ImprovedDesign;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParkingSpotManager {
    private Queue<ParkingSpot> freeSpots = new ConcurrentLinkedQueue<>();
    private Map<Integer,ParkingSpot> occupied = new ConcurrentHashMap<>();

    public ParkingSpotManager(List<ParkingSpot> spots){
        for (ParkingSpot spot : spots) {
            freeSpots.offer(spot);
        }
    }

    public ParkingSpot parkVechile(Vechile v){
        ParkingSpot spot = freeSpots.poll();
        if (spot == null) {
            throw new RuntimeException("No spots available");
        }

        synchronized(spot) {
            spot.park(v);
        }

        occupied.put(spot.getId(),spot);
        return spot;

    }

    public void freeSpot(ParkingSpot spot){
        synchronized(spot) {
            spot.remove();
        }
        occupied.remove(spot.getId());
        freeSpots.offer(spot);

    }
}
