package org.rishudesign.com.ElevatorCar;
import java.util.*;

public class Building {
    List<Floor> floors = new ArrayList<>();
    int noOfFloors;

    public Building(ExternalButtonDispatcher dispatcher,int totalFloors){
        for (int i = 1; i <= totalFloors; i++) {
            floors.add(new Floor(i, dispatcher));
        }

    }

    public Floor getFloor(int floor) {
        return floors.get(floor-1);
    }

}
