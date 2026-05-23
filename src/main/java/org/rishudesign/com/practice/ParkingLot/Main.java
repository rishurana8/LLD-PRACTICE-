package org.rishudesign.com.practice.ParkingLot;
import java.util.*;

public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        ParkingSpot spot =
                new ParkingSpot(
                        1,
                        null,
                        100,
                        ParkingStatus.AVAILABLE
                );

        ParkingSpotStrategy strategy =
                new NearesParkingSpotStrategy();

        ParkingSpotManager manager =
                new ParkingSpotManager(
                        List.of(spot),
                        strategy
                );

        Vechile car1 = new Vechile();
        car1.type = VechileType.FOUR_WHEELER;

        Vechile car2 = new Vechile();
        car2.type = VechileType.FOUR_WHEELER;

        Runnable task1 = () -> {

            ParkingSpot allocated =
                    manager.parkVechile(car1);

            if (allocated != null) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " got spot "
                                + allocated.getId()
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName()
                                + " FAILED"
                );
            }
        };

        Runnable task2 = () -> {

            ParkingSpot allocated =
                    manager.parkVechile(car2);

            if (allocated != null) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " got spot "
                                + allocated.getId()
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName()
                                + " FAILED"
                );
            }
        };

        Thread t1 = new Thread(task1);

        Thread t2 = new Thread(task2);

        t1.setName("THREAD-1");

        t2.setName("THREAD-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
