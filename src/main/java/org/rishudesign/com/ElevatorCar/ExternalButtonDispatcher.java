package org.rishudesign.com.ElevatorCar;
import org.rishudesign.com.ElevatorCar.enums.Direction;

import java.util.*;

public class ExternalButtonDispatcher {

    ElevatorScheduler scheduler;

    public ExternalButtonDispatcher(ElevatorScheduler scheduler){
        this.scheduler = scheduler;
    }

    public void submitExternalRequest(int floor , Direction dir){
        ElevatorController controller = scheduler.assignElevator(floor,dir);
        controller.submitRequest(floor);
    }


}
