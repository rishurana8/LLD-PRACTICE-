package org.rishudesign.com.practice.ParkingLot;

public class HourlyCost implements CostCalculationStrategy{
    @Override
    public double calculateCost(){
        return  60.0;
    }
}
