package org.rishudesign.com.ElevatorCar;
import org.rishudesign.com.ElevatorCar.enums.Direction;

import java.util.*;

public interface ElevatorSelectionStrategy {
    ElevatorController selectElevator(List<ElevatorController> controller, int floor, Direction dir);
}
