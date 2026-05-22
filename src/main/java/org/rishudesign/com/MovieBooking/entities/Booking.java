package org.rishudesign.com.MovieBooking.entities;
import java.util.*;
import java.util.UUID;

public class Booking {
   private UUID bookingId;
   private Show show;
   private Payment payment;
   private User user;
   private List<Integer>seats;


    public Booking(User user, Show show, List<Integer> seats, Payment payment) {
        this.bookingId = UUID.randomUUID();
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.payment = payment;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Payment getPayment() {
        return payment;
    }
}
