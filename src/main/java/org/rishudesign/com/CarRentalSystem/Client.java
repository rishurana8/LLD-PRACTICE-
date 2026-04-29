package org.rishudesign.com.CarRentalSystem;
import org.rishudesign.com.CarRentalSystem.enums.VechileType;

import java.time.LocalDate;
import java.util.*;
import static org.rishudesign.com.CarRentalSystem.enums.VechileType.FOUR_WHEELER;
import static org.rishudesign.com.CarRentalSystem.enums.VechileType.TWO_WHEELER;

public class Client {
    public static void main(String args[]){
        User user = new User(1, "Rishu");

        Location location = new Location("Ludhiana");

        List<Vechile> vehicles = List.of(
                new Vechile(1, VechileType.FOUR_WHEELER),
                new Vechile(2, VechileType.TWO_WHEELER)
        );

        Store store = new Store(1, location, vehicles);

        StoreManager manager = new StoreManager(List.of(store));

        CarRentalSystem system = new CarRentalSystem(manager);

        Booking booking = system.book(
                user,
                "Ludhiana",
                VechileType.FOUR_WHEELER,
                LocalDate.of(2026, 5, 20),
                LocalDate.of(2026, 5, 25)
        );

        if (booking != null) {
            System.out.println("Booking successful!");
        }

    }
}
