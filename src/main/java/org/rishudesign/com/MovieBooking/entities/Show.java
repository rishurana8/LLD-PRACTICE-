package org.rishudesign.com.MovieBooking.entities;
import org.rishudesign.com.MovieBooking.enums.SeatStatus;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalTime;
import java.util.concurrent.locks.ReentrantLock;

import static org.rishudesign.com.MovieBooking.enums.SeatStatus.AVAILABLE;
import static org.rishudesign.com.MovieBooking.enums.SeatStatus.BOOKED;

public class Show {
    private int id;
    private Movie movie;
    private Screen screen;
    private LocalTime startTime;
    private  LocalDate showDate;

    private final Map<Integer, SeatStatus>seatStatusMap = new HashMap<>();
    private final Map<Integer, ReentrantLock>seatLocks = new HashMap<>();


    public Show(Movie movie, Screen screen, LocalDate date, LocalTime time) {
        this.movie = movie;
        this.showDate = date;
        this.startTime = time;

        for (Seat seat : screen.getSeats()) {
            seatStatusMap.put(seat.getId(), SeatStatus.AVAILABLE);
            seatLocks.put(seat.getId(), new ReentrantLock());
        }
    }

    public Movie getMovie() {
        return movie;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public boolean lockSeats(List<Integer> seatIds){
        List<Integer> sorted = new ArrayList<>(seatIds);
        Collections.sort(sorted);

        List<ReentrantLock> acquiredLocks = new ArrayList<>();
        try{
            for(int seatId: sorted){
                ReentrantLock  lock = seatLocks.get(seatId);
                lock.lock();
                acquiredLocks.add(lock);
            }

             for(int seatId: sorted){
                if(seatStatusMap.get(seatId)!= AVAILABLE){
                    return false;
                }
             }

        for(int seatId: sorted){
             seatStatusMap.put(seatId,SeatStatus.LOCKED);
        }
            return true;

        }finally{
           for(ReentrantLock lock: acquiredLocks){
                   lock.unlock();
           }
        }

    }

    public void confirmSeats(List<Integer>seats){
        for(Integer seatId: seats){
            seatStatusMap.put(seatId,BOOKED);
        }
        System.out.println("Seats are confirmed");
    }

    public void releaseSeats(List<Integer>seats){
        for(Integer seatId: seats){
            seatStatusMap.put(seatId,AVAILABLE);
        }
    }


}
