package org.rishudesign.com.MovieBooking.entities;

import java.time.LocalDate;
import java.util.*;

public class Screen {
    private  int id;
    private final List<Seat> seats;
    private final Map<LocalDate, List<Show>> showsByDate = new HashMap<>();

    public Screen(int screenId, List<Seat> seats) {
        this.id = screenId;
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void addShow(Show show) {
        showsByDate
                .computeIfAbsent(show.getShowDate(), d -> new ArrayList<>())
                .add(show);
    }

    public List<Show> getShows(LocalDate date) {
        return showsByDate.getOrDefault(date, List.of());
    }
}
