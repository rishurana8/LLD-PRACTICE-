package org.rishudesign.com.ElevatorCar;

import org.rishudesign.com.ElevatorCar.enums.Direction;

public class ExternalButton {
   ExternalButtonDispatcher dispatcher;

   public ExternalButton(ExternalButtonDispatcher dispatcher){
       this.dispatcher = dispatcher;
   }

   public void PressButton(int floor , Direction dir){
       dispatcher.submitExternalRequest(floor,dir);
   }
}
