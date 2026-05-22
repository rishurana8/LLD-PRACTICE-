package org.rishudesign.com.MovieBooking.service;
import java.util.*;
import org.rishudesign.com.MovieBooking.entities.Booking;
import org.rishudesign.com.MovieBooking.entities.Payment;
import org.rishudesign.com.MovieBooking.entities.Show;
import org.rishudesign.com.MovieBooking.entities.User;

import java.util.UUID;

import static org.rishudesign.com.MovieBooking.enums.PaymentStatus.SUCCESS;

public class BookingService {
   private Map<UUID, Booking> bookings  = new HashMap<>();


   public Booking book(Show show , User user , List<Integer>seats){
       if(!show.lockSeats(seats)){
           throw new RuntimeException("Seats are not avaialbe");
       }

       Payment payment = new Payment(SUCCESS);

       if(payment.getStatus() == SUCCESS){
            show.confirmSeats(seats);
            Booking booking = new Booking(user,show,seats,payment);
            bookings.put(booking.getBookingId(),booking);
            return booking;
       }else{
          show.releaseSeats(seats);
           throw new RuntimeException("Payment failed");
       }


   }

   public Booking getBooking(UUID bookingId){ return bookings.get(bookingId);}

    public List<Booking> getBookingForUser(User user){
       return bookings.values().stream().filter(x ->x.getUser().equals(user)).toList();
    }
}
