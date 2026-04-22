package org.rishudesign.com.ParkingLot.CostComputation;

import org.rishudesign.com.ParkingLot.OtherClasses.Ticket;
import org.rishudesign.com.ParkingLot.ParkingSpots.ParkingSpot;
import org.rishudesign.com.ParkingLot.enums.DurationType;

public class ExitGate {
    private PricingStrategy pricingStrategy;

    public ExitGate(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    /**
     * Calculate parking cost based on entry/exit time and vehicle type
     * @param ticket - Contains vehicle info, entry time, and parking spot
     * @return - Cost of parking
     */
    public double calculateCost(Ticket ticket) {
        // Get entry time from ticket
        long entryTime = ticket.getEntryTime();

        // Get current exit time
        long exitTime = System.currentTimeMillis();

        // Calculate duration in minutes
        long durationInMinutes = (exitTime - entryTime) / (1000 * 60);

        // Determine if it's hourly or minute-based (usually you'd have rules for this)
        // For now, let's assume: if duration < 60 min, charge per minute; else per hour
        DurationType durationType = durationInMinutes < 60 ? DurationType.MINUTE : DurationType.HOURLY;

        // Convert duration to appropriate unit
        int duration = (int) (durationType == DurationType.HOURLY 
                            ? Math.ceil(durationInMinutes / 60.0) 
                            : durationInMinutes);

        // Get vehicle type
        String vehicleType = ticket.getVechile().getVechileType().toString().toLowerCase();

        // Calculate price using strategy pattern
        double cost = pricingStrategy.calculatePrice(vehicleType, durationType, duration);

        return cost;
    }

    /**
     * Process vehicle exit: calculate cost and remove vehicle from parking spot
     * @param ticket - Parking ticket
     * @return - Final cost to be paid
     */
    public double processExit(Ticket ticket) {
        // Calculate the cost
        double cost = calculateCost(ticket);

        // Remove vehicle from parking spot
        ParkingSpot spot = ticket.getSpot();
        if (spot != null) {
            spot.removeVechile(ticket.getVechile());
        }

        // Return the cost to be paid
        return cost;
    }

    /**
     * Generate exit receipt
     * @param ticket - Parking ticket
     * @param cost - Cost of parking
     * @return - Receipt details
     */
    public String generateReceipt(Ticket ticket, double cost) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("========== PARKING RECEIPT ==========\n");
        receipt.append("Vehicle Number: ").append(ticket.getVechile().getVechileNo()).append("\n");
        receipt.append("Vehicle Type: ").append(ticket.getVechile().getVechileType()).append("\n");
        receipt.append("Entry Time: ").append(new java.util.Date(ticket.getEntryTime())).append("\n");
        receipt.append("Exit Time: ").append(new java.util.Date(System.currentTimeMillis())).append("\n");
        receipt.append("Parking Spot ID: ").append(ticket.getSpot()).append("\n");
        receipt.append("Cost: $").append(String.format("%.2f", cost)).append("\n");
        receipt.append("====================================\n");
        return receipt.toString();
    }
}
