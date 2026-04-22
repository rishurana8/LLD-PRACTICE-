package org.rishudesign.com.ParkingLot.CostComputation;

import org.rishudesign.com.ParkingLot.enums.DurationType;




public class HourlyPrice implements PricingStrategy{

    @Override
    public double calculatePrice(String vechileType, DurationType durationType,int duration){
          switch(vechileType){
              case "bike":
              return durationType == DurationType.HOURLY? duration*10.0: duration*10.0*4;
              case "car":
                  return durationType == DurationType.HOURLY? duration*20.0: duration*20.0*4;
              default:
                  return durationType == DurationType.HOURLY
                          ? duration * 15.0   // $15 per hour for other vehicles
                          : duration * 15.0 * 24;  // Daily rate
          }
    }
}
