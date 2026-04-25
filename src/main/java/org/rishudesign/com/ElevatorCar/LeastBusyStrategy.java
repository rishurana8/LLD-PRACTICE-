package org.rishudesign.com.ElevatorCar;

import org.rishudesign.com.ElevatorCar.enums.Direction;

import java.util.List;

public class LeastBusyStrategy implements ElevatorSelectionStrategy{
    @Override
    public ElevatorController selectElevator(List<ElevatorController> controllers,
                                             int requestFloor,
                                             Direction direction) {

        ElevatorController best = null;
        int minLoad = Integer.MAX_VALUE;

        for (ElevatorController controller : controllers) {
            int load = controller.upMinPQ.size() +
                    controller.downMaxPQ.size();

            if (load < minLoad) {
                minLoad = load;
                best = controller;
            }
        }

        return best;
    }
}