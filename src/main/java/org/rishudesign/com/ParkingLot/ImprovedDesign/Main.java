package org.rishudesign.com.ParkingLot.ImprovedDesign;
import java.util.*;

public class Main {
    public static void main(String args[]){
        List<ParkingSpot> bikeSpots = List.of(
                new ParkingSpot(1, VechileType.TWO_WHEELER),
                new ParkingSpot(2, VechileType.TWO_WHEELER)
        );

        List<ParkingSpot> carSpots = List.of(
                new ParkingSpot(3, VechileType.FOUR_WHEELER),
                new ParkingSpot(4, VechileType.FOUR_WHEELER)
        );

        Map<VechileType, ParkingSpotManager> managers = new HashMap<>();
        managers.put(VechileType.TWO_WHEELER, new ParkingSpotManager(bikeSpots));
        managers.put(VechileType.FOUR_WHEELER, new ParkingSpotManager(carSpots));

        ParkingLot lot = new ParkingLot(managers);

        EntryGate gate1= new EntryGate(lot);
        ExitGate exitgate = new ExitGate(new HourlyPricing(),lot);

        Vechile v = new Vechile(VechileType.TWO_WHEELER,"B234");

        Ticket ticket = gate1.parkVechile(v);

        double cost = exitgate.processExit(ticket);

        System.out.println("Cost: " + cost);

    }
}
