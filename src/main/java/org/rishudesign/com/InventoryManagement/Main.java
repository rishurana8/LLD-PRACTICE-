package org.rishudesign.com.InventoryManagement;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args)
            throws InterruptedException {

        Warehouse warehouse = new Warehouse();

        Product iphone =
                new Product(1, "Iphone", 100000);

        Product macbook =
                new Product(2, "Macbook", 200000);

        warehouse.addProduct(iphone, 10);
        warehouse.addProduct(macbook, 5);

        ReservationService reservationService =
                new ReservationService(
                        warehouse
                );

        Runnable user1 = () -> {

            Map<Integer, Integer> cart =
                    new HashMap<>();

            cart.put(1, 7);

            Reservation reservation =
                    reservationService.reserveProducts(cart);

            if (reservation != null) {

                System.out.println(
                        "User1 Reserved Successfully"
                );

                reservationService.confirmReservation(
                        reservation.getReservationId()
                );

            } else {

                System.out.println(
                        "User1 Reservation Failed"
                );
            }
        };

        Runnable user2 = () -> {

            Map<Integer, Integer> cart =
                    new HashMap<>();

            cart.put(1, 5);

            Reservation reservation =
                    reservationService.reserveProducts(cart);

            if (reservation != null) {

                System.out.println(
                        "User2 Reserved Successfully"
                );

                reservationService.confirmReservation(
                        reservation.getReservationId()
                );

            } else {

                System.out.println(
                        "User2 Reservation Failed"
                );
            }
        };

        Thread t1 = new Thread(user1);
        Thread t2 = new Thread(user2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Inventory inventory =
                warehouse.getInventory(1);

        System.out.println(
                "Remaining Quantity : "
                        + inventory.getAvailableQuantity()
        );
    }
}
