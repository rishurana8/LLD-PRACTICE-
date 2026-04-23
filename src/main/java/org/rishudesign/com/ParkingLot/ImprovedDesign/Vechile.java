package org.rishudesign.com.ParkingLot.ImprovedDesign;

public class Vechile {
    private VechileType type;
    private String number;

    public Vechile(VechileType type,String number){
        this.type = type;
        this.number = number;
    }

    public VechileType getType(){
        return this.type;
    }

    public String getNo(){
        return this.number;
    }
}
