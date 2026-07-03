package org.rishudesign.com.CarRentalSystem;
import org.rishudesign.com.CarRentalSystem.enums.VechileType;

import java.time.LocalDate;
import java.util.*;
import static org.rishudesign.com.CarRentalSystem.enums.VechileType.FOUR_WHEELER;
import static org.rishudesign.com.CarRentalSystem.enums.VechileType.TWO_WHEELER;

public class Client {
    public static void main(String args[]){
        User user = new User(1, "Rishu");
        User user2 = new User(2,"aryan");

        Location location = new Location("Ludhiana");

        List<Vechile> vehicles = List.of(
                new Vechile(1, VechileType.FOUR_WHEELER),
                new Vechile(2, VechileType.TWO_WHEELER)
        );

        Store store = new Store(1, location, vehicles);

        StoreManager manager = new StoreManager(List.of(store));

        CarRentalSystem system = new CarRentalSystem(manager);


        LocalDate startDate = LocalDate.of(2026,5,20);
        LocalDate endDate = LocalDate.of(2026,5,25);


//        LocalDate startDate2 = LocalDate.of(2026,5,10);
//        LocalDate endDate2 = LocalDate.of(2026,5,15);

        Runnable r1 = ()->{
            Booking booking = system.book(user,"Ludhiana",VechileType.FOUR_WHEELER,startDate,endDate);
            if(booking==null){
                System.out.println("booking was not successful");
            }else{
                System.out.println("Booking is successfull for " + Optional.ofNullable(booking.getUser().getName()));
            }
        };


        Runnable r2 = ()->{
            Booking booking = system.book(user2,"Ludhaina",VechileType.FOUR_WHEELER,startDate,endDate);
            if(booking ==null){
                System.out.println("booking was not successful for aaryan" );
            }else{
                System.out.println("Booking is successfull for " + Optional.ofNullable(booking.getUser().getName()));
            }
        };

        Thread t1= new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();



    }
}
