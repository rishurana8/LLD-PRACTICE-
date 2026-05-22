package org.rishudesign.com.MovieBooking.entities;

import org.rishudesign.com.MovieBooking.enums.SeatCategory;

public class Seat {
    private int seatId;
    private SeatCategory category;

    public Seat(int seatId,SeatCategory category){
        this.seatId = seatId;
        this.category = category;
    }
    public int getId(){
        return this.seatId;
    }


}
