package org.rishudesign.com.MovieBooking;

import org.rishudesign.com.MovieBooking.entities.*;
import org.rishudesign.com.MovieBooking.enums.SeatCategory;
import org.rishudesign.com.MovieBooking.service.BookingService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingMyshowMultithreading {
    public static void main(String[] args) throws InterruptedException {

        // Create movie
        Movie movie = new Movie("Avengers");

        // Create screen
        Screen screen = new Screen(1, createSeats());

        // Create show
        Show show = new Show(
                movie,
                screen,
                LocalDate.now(),
                LocalTime.now()
        );

        // Booking service
        BookingService bookingService = new BookingService();

        // Users
        User user1 = new User("U1", "Rishu");
        User user2 = new User("U2", "Rahul");

        // SAME seats requested
        List<Integer> seats = List.of(1, 2, 3);

        // Thread 1
        Thread t1 = new Thread(() -> {
            try {
                Booking booking = bookingService.book(show, user1, seats);

                System.out.println(
                        "SUCCESS: " +
                                user1.getName() +
                                " booked seats " + seats
                );

            } catch (Exception e) {

                System.out.println(
                        "FAILED: " +
                                user1.getName() +
                                " could not book seats"
                );
            }
        });

        // Thread 2
        Thread t2 = new Thread(() -> {
            try {

                Booking booking = bookingService.book(show, user2, seats);

                System.out.println(
                        "SUCCESS: " +
                                user2.getName() +
                                " booked seats " + seats
                );

            } catch (Exception e) {

                System.out.println(
                        "FAILED: " +
                                user2.getName() +
                                " could not book seats"
                );
            }
        });

        // Start BOTH simultaneously
        t1.start();
        t2.start();

        // Wait for completion
        t1.join();
        t2.join();
    }

    private static List<Seat> createSeats() {

        return List.of(
                new Seat(1, SeatCategory.SILVER),
                new Seat(2, SeatCategory.SILVER),
                new Seat(3, SeatCategory.SILVER),
                new Seat(4, SeatCategory.SILVER),
                new Seat(5, SeatCategory.SILVER)
        );
    }
}
