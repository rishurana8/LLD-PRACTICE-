package org.rishudesign.com.ElevatorCar;
import org.rishudesign.com.ElevatorCar.enums.Direction;

import java.util.*;

public class ElevatorScheduler {
    private List<ElevatorController> controller;
    private ElevatorSelectionStrategy strategy;

    public ElevatorScheduler(List<ElevatorController>controller,ElevatorSelectionStrategy strategy){
        this.controller = controller;
        this.strategy = strategy;
    }

    public void setStrategy(ElevatorSelectionStrategy strategy){
        this.strategy = strategy;
    }

    public ElevatorController assignElevator(int floor,  Direction direction) {
        return strategy.selectElevator(controller, floor, direction);
    }

}
