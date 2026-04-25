package org.rishudesign.com.ElevatorCar;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String args[]){

        try {
            ElevatorCar car1 = new ElevatorCar(1);
            ElevatorCar car2 = new ElevatorCar(2);



            ElevatorController controller1 = new ElevatorController(car1);
            ElevatorController controller2 = new ElevatorController(car2);

            InternalButton internalbutton1 = new InternalButton(controller1);
            InternalButton internalbutton2 = new InternalButton(controller2);

            List<ElevatorController> controllers = new ArrayList<>();
            controllers.add(controller1);
            controllers.add(controller2);

            ElevatorSelectionStrategy strategy = new LeastBusyStrategy();

            ElevatorScheduler scheduler = new ElevatorScheduler(controllers, strategy);

            ExternalButtonDispatcher dispatcher = new ExternalButtonDispatcher(scheduler);

            Building building = new Building(dispatcher, 5);

            new Thread(controller1, "Elevator 1").start();
            new Thread(controller2, "Elevator 2").start();

            building.getFloor(3).PressUpButton();

            Thread.sleep(5);

            building.getFloor(5).pressDownButton();
            Thread.sleep(5);

            internalbutton1.pressButton(4);



        }catch(Exception e){

        }
    }
}
