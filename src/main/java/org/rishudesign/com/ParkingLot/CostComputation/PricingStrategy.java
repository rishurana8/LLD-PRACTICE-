package org.rishudesign.com.ParkingLot.CostComputation;

import org.rishudesign.com.ParkingLot.enums.DurationType;
import org.rishudesign.com.ParkingLot.enums.VechileType;

public interface PricingStrategy {
    public double calculatePrice(String vechileType, DurationType durationType,int duration);
}
