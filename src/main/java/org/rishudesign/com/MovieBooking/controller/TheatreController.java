package org.rishudesign.com.MovieBooking.controller;

import org.rishudesign.com.MovieBooking.entities.Movie;
import org.rishudesign.com.MovieBooking.entities.Show;
import org.rishudesign.com.MovieBooking.entities.Theatre;
import org.rishudesign.com.MovieBooking.enums.City;
import org.rishudesign.com.MovieBooking.service.TheatreService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController() {
        this.theatreService = new TheatreService();
    }

    public void addTheatre(Theatre theatre) {
        theatreService.addTheatre(theatre);
    }

    public Set<Movie> getMovies(City city, LocalDate date) {
        return theatreService.getMovies(city, date);
    }

    public List<Theatre> getTheatres(City city, Movie movie, LocalDate date) {
        return theatreService.getTheatres(city, movie, date);
    }

    public List<Show> getShows(Movie movie, LocalDate date, Theatre theatre) {
        return theatreService.getShows(movie, date, theatre);
    }
}
