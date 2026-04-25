package org.rishudesign.com.ElevatorCar;

import org.rishudesign.com.ElevatorCar.enums.Direction;
import org.rishudesign.com.ElevatorCar.enums.Status;

import static org.rishudesign.com.ElevatorCar.enums.Direction.DOWN;
import static org.rishudesign.com.ElevatorCar.enums.Direction.UP;
import static org.rishudesign.com.ElevatorCar.enums.Status.IDLE;
import static org.rishudesign.com.ElevatorCar.enums.Status.MOVING;

public class ElevatorCar {
    int id;
    Status status;
    Display display;
    int currentFloor;
    Direction dir;
    int nextFloorStopage;
    InternalButton button;

    public ElevatorCar(int id){
        this.id = id;
        currentFloor = 0;
        status = IDLE;
        display = new Display(currentFloor,dir);
    }

    public void clickButton(int currentFloor){
        button.pressButton(currentFloor);
    }

    public void showDisplay(){
         display.showDisplay();
    }

   public void moveElevator(int destinationFloor){
       this.nextFloorStopage = destinationFloor;
       if(this.currentFloor == destinationFloor){
           return;
       }

       int startFloor = currentFloor;
       if(nextFloorStopage>=currentFloor){
           this.status = MOVING;
           this.dir = UP;
           for(int i = startFloor+1;i<=nextFloorStopage;i++){
               try{
                   Thread.sleep(5);
               }catch(Exception e){

               }
               showDisplay();
               setCurrentFloor(i);
           }

       }else{
           this.dir = DOWN;
           this.status = MOVING;
           for(int i=startFloor-1;i>=nextFloorStopage;i--){
               try{
                   Thread.sleep(5);
               }catch(Exception e){

               }
               showDisplay();
               setCurrentFloor(i);
           }
       }

   }

   public void setCurrentFloor(int i){
        this.currentFloor = i;
   }

}
