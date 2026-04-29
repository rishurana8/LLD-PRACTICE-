package org.rishudesign.com.CarRentalSystem;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private LocalDate  from;
    private LocalDate to;
    private User user;
    private Vechile vechile;

    public Booking(int bookingId,LocalDate  from, LocalDate  to,User user,Vechile vechile){
        this.bookingId = bookingId;
        this.from = from;
        this.to = to;
        this.user= user;
        this.vechile = vechile;
    }

    public LocalDate getFrom() { return from; }
    public LocalDate getTo() { return to; }

}
