package org.rishudesign.com.MovieBooking.controller;

import org.rishudesign.com.MovieBooking.entities.Booking;
import org.rishudesign.com.MovieBooking.entities.Show;
import org.rishudesign.com.MovieBooking.entities.User;
import org.rishudesign.com.MovieBooking.service.BookingService;

import java.util.List;
import java.util.UUID;

public class BookingController {
    private final BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService();
    }

    public Booking createBooking(User user, Show show, List<Integer> seats) {
        Booking booking = bookingService.book(show, user, seats);
        return booking;
    }

    public Booking getBooking(UUID bookingId) {
        return bookingService.getBooking(bookingId);
    }

    public List<Booking> getBookingsForUser(User user) {
        return bookingService.getBookingForUser(user);
    }
}
