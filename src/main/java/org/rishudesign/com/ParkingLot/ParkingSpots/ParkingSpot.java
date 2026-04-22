package org.rishudesign.com.ParkingLot.ParkingSpots;

import org.rishudesign.com.ParkingLot.enums.VechileType;

public abstract class ParkingSpot {
    private int id;
    private float price;
    private VechileType vechileType;
    private boolean isEmpty;
    private Vechile vechile;

    public ParkingSpot(int id,VechileType vechileType){
       this.id = id;
       this.vechileType = vechileType;
       this.isEmpty = true;
    }

    public  void parkVechile(Vechile vechile) {
        if(!isEmpty){
           throw new IllegalStateException("Spot is no Empty");
        }

        if(!canParkVechile(vechile.getVechileType())) {
            throw new IllegalStateException("Cannot park this kind of vechile here");
        }
        this.vechile = vechile;
        this.isEmpty = false;
        return;
    }

    public  void removeVechile(Vechile vechile){
        if(isEmpty){
            throw new IllegalStateException("This spot is already empty");
        }

        this.vechile = null;
        this.isEmpty = true;
        System.out.println("The vechile is removed");
    }

    public boolean isAvailable(){
        return this.isEmpty;
    }

    public Vechile getVechile(){
        return this.vechile;
    }

    public abstract boolean canParkVechile(VechileType vechiletype);
}
