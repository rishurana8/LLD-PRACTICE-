package org.rishudesign.com.ElevatorCar;

public class InternalButton {

    private final ElevatorController controller;

    public InternalButton(ElevatorController controller) {
        this.controller = controller;
    }



    public void pressButton(int floor){
        InternalButtonDispatcher.getInstance().submitRequest(controller,floor);
    }

}
