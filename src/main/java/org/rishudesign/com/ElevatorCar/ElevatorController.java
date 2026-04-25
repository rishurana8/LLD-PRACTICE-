package org.rishudesign.com.ElevatorCar;

import java.util.concurrent.PriorityBlockingQueue;

import static org.rishudesign.com.ElevatorCar.enums.Status.IDLE;

public class ElevatorController implements Runnable {

    PriorityBlockingQueue<Integer> upMinPQ;
    PriorityBlockingQueue<Integer> downMaxPQ;

    private final Object monitor = new Object();

    ElevatorCar car;

    public ElevatorController(ElevatorCar car){
        upMinPQ = new PriorityBlockingQueue<Integer>();
        downMaxPQ  = new PriorityBlockingQueue<Integer>(10,(b,a)->(b-a));
        this.car = car;
    }

    public void enqueueRequest(int destinationFloor){
        if(destinationFloor == car.nextFloorStopage){
            return;
        }

        if(destinationFloor>=car.nextFloorStopage){
            if(!upMinPQ.contains(destinationFloor)){
                upMinPQ.offer(destinationFloor);
            }
        }else{
            if(!downMaxPQ.contains(destinationFloor)){
                downMaxPQ.offer(destinationFloor);
            }
        }
        synchronized (monitor) {
            monitor.notify();   // wake elevator thread
        }

    }

    public void submitRequest(int destinationFloor){
        enqueueRequest(destinationFloor);
    }

    @Override
    public void run(){
      controlElevator();
    }

    public void controlElevator(){
        while(true){
            synchronized(monitor){
              while(upMinPQ.isEmpty() && downMaxPQ.isEmpty()){
                  try{
                      System.out.println("elevator:" + car.id + " is IDLE");
                      car.status = IDLE;
                      monitor.wait();
                  }catch(Exception e){

                  }
              }

              while(!upMinPQ.isEmpty()){
                  int floor = upMinPQ.poll();
                  System.out.println("Serving floor: " + floor + " by elevator:" + car.id + " currentFloor: " + car.currentFloor);
                  car.moveElevator(floor);
              }

                while (!downMaxPQ.isEmpty()) {
                    int floor = downMaxPQ.poll();
                    System.out.println("Serving floor: " + floor + " by elevator:" + car.id + " currentFloor: " + car.currentFloor);
                    car.moveElevator(floor);
                }
            }
        }
    }
}
