package org.rishudesign.com.ParkingLot.ImprovedDesign;

public class ParkingSpot {
    private int id;
    private VechileType type;
    private SpotStatus status;
    private Vechile vechile;

    public ParkingSpot(int id,VechileType type){
        this.id = id;
        this.type = type;
        this.status= SpotStatus.FREE;
    }

    public synchronized boolean park(Vechile v){
        if(status.equals(SpotStatus.OCCUPIED)){
            return false;
        }
         this.vechile = v;
        this.status = SpotStatus.OCCUPIED;
        return true;
    }

    public synchronized void remove(){
        if(status.equals(SpotStatus.FREE)){
            System.out.println("This Spot is already empty");
            return;
        }
        this.vechile = null;
        this.status = SpotStatus.FREE;
    }

    public boolean isFree(){
        return status.equals(SpotStatus.FREE);
    }

    public int getId(){
        return this.id;
    }
}
