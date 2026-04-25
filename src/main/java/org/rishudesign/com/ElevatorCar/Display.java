package org.rishudesign.com.ElevatorCar;

import org.rishudesign.com.ElevatorCar.enums.Direction;

public class Display {
    int floor;
    Direction dir;

    public Display(int floor,Direction dir){
        this.floor = floor;
        this.dir = dir;
    }

    public void showDisplay(){
        System.out.println("Current floor is " + floor);
        System.out.println("Current Direction is "  + dir);
    }
}
