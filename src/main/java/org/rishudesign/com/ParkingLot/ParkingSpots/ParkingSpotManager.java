package org.rishudesign.com.ParkingLot.ParkingSpots;
import java.util.*;


public class ParkingSpotManager {
    private List<ParkingSpot>parkingSpots;

     public ParkingSpotManager(List<ParkingSpot>parkingSpots){
         this.parkingSpots = parkingSpots;
     }

     public ParkingSpot parkVechile(Vechile vechile){
         ParkingSpot spot = findAvailabeSpot();
         if(spot!=null) {
             spot.parkVechile(vechile);
             System.out.println("Vechile Parked");
         }
         return spot;

     }

     public ParkingSpot findAvailabeSpot(){
        for( ParkingSpot spot: parkingSpots){
            if(spot.isAvailable()){
                return spot;
            }
        }
        return null;
     }

     public void removeVechile(ParkingSpot spot , Vechile vechile){
         if(!spot.isAvailable() && spot.getVechile().equals(vechile)){
             spot.removeVechile(vechile);
             System.out.println("vechile removed");
         }else{
             throw new IllegalStateException("It is an illegal move");
         }
         return;
     }

     public void addParkingSpot(ParkingSpot spot){
         parkingSpots.add(spot);
     }

     public void removeParkingSpot(ParkingSpot spot){
         parkingSpots.remove(spot);
     }
}
