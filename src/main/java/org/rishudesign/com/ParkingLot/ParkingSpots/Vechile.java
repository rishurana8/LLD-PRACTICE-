package org.rishudesign.com.ParkingLot.ParkingSpots;

import org.rishudesign.com.ParkingLot.enums.VechileType;

public abstract class Vechile {
    private String vechileNo;
    private VechileType vechileType;

    public Vechile(String vechileNo,VechileType vechileType){
        this.vechileNo = vechileNo;
        this.vechileType = vechileType;
    }

    public VechileType getVechileType(){
        return this.vechileType;
    }

    public String getVechileNo(){
        return this.vechileNo;
    }
}
