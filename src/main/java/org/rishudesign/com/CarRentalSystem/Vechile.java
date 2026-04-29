package org.rishudesign.com.CarRentalSystem;

import org.rishudesign.com.CarRentalSystem.enums.VechileType;

public class Vechile {
    int vechileNo;
    VechileType vechileType;

    public Vechile(int vechileNo,VechileType vechileType){
        this.vechileNo = vechileNo;
        this.vechileType= vechileType;
    }

    public VechileType getVechileType(){
        return this.vechileType;
    }

    public int getVechileNo(){
        return this.vechileNo;
    }

}
