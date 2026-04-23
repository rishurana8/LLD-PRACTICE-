package org.rishudesign.com.ParkingLot.ImprovedDesign;

public class ExitGate {
    private PricingStrategy pricingStrategy;
    private ParkingLot parkingLot;

    public ExitGate(PricingStrategy pricingStrategy,ParkingLot lot){
        this.pricingStrategy = pricingStrategy;
        this.parkingLot = lot;
    }

    public double processExit(Ticket ticket){
        long now = System.currentTimeMillis();
        long duration = (now - ticket.getEntryTime()) / (1000 * 60);

        double cost = pricingStrategy.calculate(ticket.getVechile().getType(),duration);
        parkingLot.exit(ticket.getSpot(), ticket.getVechile());
        return cost;
    }
}
