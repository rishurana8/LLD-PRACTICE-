package org.rishudesign.com.CarRentalSystem;

import org.rishudesign.com.CarRentalSystem.enums.VechileType;

import java.time.LocalDate;
import java.util.Date;

public class CarRentalSystem {

    private final StoreManager storeManager;

    public CarRentalSystem(StoreManager manager) {
        this.storeManager = manager;
    }

    public Booking book(User user, String city,
                        VechileType type,
                        LocalDate from, LocalDate to) {

        Store store = storeManager.findByCity(city);

        if (store == null) {
            System.out.println("No store found");
            return null;
        }

        Booking booking = store.book(type, from, to,user);

        if (booking == null) {
            System.out.println("No vehicle available");
        }

        return booking;
    }

}
