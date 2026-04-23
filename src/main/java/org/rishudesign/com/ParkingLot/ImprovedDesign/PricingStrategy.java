package org.rishudesign.com.ParkingLot.ImprovedDesign;

public interface PricingStrategy {
    double calculate(VechileType type, long durationInMinutes);
}
