package org.rishudesign.com.ParkingLot.ImprovedDesign;

public class HourlyPricing implements PricingStrategy{
    public double calculate(VechileType type, long duration) {
        double rate = (type == VechileType.TWO_WHEELER) ? 10 : 20;
        return Math.ceil(duration / 60.0) * rate;
    }
}
