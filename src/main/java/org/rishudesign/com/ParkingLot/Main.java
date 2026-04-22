package org.rishudesign.com.ParkingLot;
import org.rishudesign.com.ParkingLot.CostComputation.ExitGate;
import org.rishudesign.com.ParkingLot.CostComputation.HourlyPrice;
import org.rishudesign.com.ParkingLot.CostComputation.PricingStrategy;
import org.rishudesign.com.ParkingLot.OtherClasses.Ticket;
import org.rishudesign.com.ParkingLot.ParkingSpots.*;
import org.rishudesign.com.ParkingLot.enums.VechileType;

import java.util.*;

public class Main {
    public static void main(String args[]){
         List<ParkingSpot> TwoWheelerspots = new ArrayList<>();
         ParkingSpot spot1 = new TwoWheelerParkingSpot(1, VechileType.TWO_WHEELER);
         ParkingSpot spot2 = new TwoWheelerParkingSpot(2, VechileType.TWO_WHEELER);

         List<ParkingSpot> FourWheelerspots = new ArrayList<>();
         ParkingSpot spot3 = new FourWheelerParkingSpot(3, VechileType.FOUR_WHEELER);
        ParkingSpot spot4 = new FourWheelerParkingSpot(4, VechileType.FOUR_WHEELER);

        TwoWheelerspots.add(spot1);
        TwoWheelerspots.add(spot2);

        FourWheelerspots.add(spot3);
        FourWheelerspots.add(spot4);

        ParkingSpotManager parkingManager1 = new TwoWheerlerManager(TwoWheelerspots);
        ParkingSpotManager parkingManager2 = new FourWheelerManager(FourWheelerspots);

        Vechile bike = new Bike("SZ2345");
        Vechile Car = new Car("BR32345");

        EntryGate gate1 = new EntryGate(bike);
        gate1.ParkVechile(TwoWheelerspots);
        Ticket ticket1 = gate1.GenerateTicket();

         EntryGate gate2 = new EntryGate(Car);
         gate2.ParkVechile(FourWheelerspots);
         Ticket ticket2 = gate2.GenerateTicket();

        PricingStrategy strategy = new HourlyPrice();
        ExitGate exitgate1 = new ExitGate(strategy);
        double cost1 = exitgate1.processExit(ticket1);
        System.out.println("Cost to be paid is : "+ cost1);


    }
}
