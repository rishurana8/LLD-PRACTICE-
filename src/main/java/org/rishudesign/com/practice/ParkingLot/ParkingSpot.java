package org.rishudesign.com.practice.ParkingLot;

import static org.rishudesign.com.practice.ParkingLot.ParkingStatus.AVAILABLE;
import static org.rishudesign.com.practice.ParkingLot.ParkingStatus.OCCUPIED;

public  class ParkingSpot {
       public int id;
       public Vechile vechile;
       public double price;
       public ParkingStatus status;

       public ParkingSpot(int id,Vechile vechile,double price,ParkingStatus status){
          this.id = id;
          this.vechile = vechile;
          this.price = price;
          this.status = status;
       }

       public  ParkingSpot parkVechile(Vechile v){
           this.vechile = v;
           this.status = OCCUPIED;
           return this;
       }

     public int getId(){
           return this.id;
     }

     public void freeVechile(){
           this.vechile = null;
           this.status   = AVAILABLE;
     }
}
