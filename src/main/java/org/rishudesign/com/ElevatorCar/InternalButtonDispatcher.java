package org.rishudesign.com.ElevatorCar;
import java.util.*;

public class InternalButtonDispatcher {
    private static InternalButtonDispatcher INSTANCE = new InternalButtonDispatcher();

    private InternalButtonDispatcher() {}

    public static InternalButtonDispatcher getInstance() {
        return INSTANCE;
    }


    public void submitRequest(ElevatorController controller,int destinationFloor){
        controller.submitRequest(destinationFloor);
    }
}
