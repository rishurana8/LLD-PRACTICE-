package org.rishudesign.com.ElevatorCar;

import org.rishudesign.com.ElevatorCar.enums.Direction;

public class Floor {
    int floor;
    ExternalButton upButton;
    ExternalButton downButton;

    public Floor(int floorNo,ExternalButtonDispatcher dispatcher){
        this.floor = floorNo;
        upButton = new ExternalButton(dispatcher);
        downButton = new ExternalButton(dispatcher);
    }

    public void PressUpButton(){
        upButton.PressButton(floor, Direction.UP);
    }

    public void pressDownButton(){
        downButton.PressButton(floor,Direction.DOWN);
    }
}
