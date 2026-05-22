package org.rishudesign.com.MovieBooking.service;
import org.rishudesign.com.MovieBooking.entities.Movie;
import org.rishudesign.com.MovieBooking.entities.Screen;
import org.rishudesign.com.MovieBooking.entities.Show;
import org.rishudesign.com.MovieBooking.entities.Theatre;
import org.rishudesign.com.MovieBooking.enums.City;

import java.time.LocalDate;
import java.util.*;

public class TheatreService {
   private Map<City,List<Theatre>>cityTheatres = new HashMap<>();

    public void addTheatre(Theatre theatre){
        cityTheatres.computeIfAbsent(theatre.getCity(),c -> new ArrayList<>()).add(theatre);
    }


    public Set<Movie> getMovies(City city, LocalDate showdate){
        Set<Movie> movies = new HashSet<>();
        List<Theatre> theatres = cityTheatres.get(city);


        for(Theatre theatre : theatres){
              for(Screen screen: theatre.getScreen()){
                  for(Show show: screen.getShows(showdate)){
                        movies.add(show.getMovie());
                  }
              }
        }
        return movies;
    }

   public List<Theatre> getTheatres(City city, Movie movie, LocalDate date){
       List<Theatre> theatres = cityTheatres.get(city);
       List<Theatre> availableTheatre = new ArrayList<>();
       for(Theatre theatre: theatres){
           for(Screen screen : theatre.getScreen()){
               for(Show show: screen.getShows(date)){
                   if(movie.equals(show.getMovie())){
                       availableTheatre.add(theatre);
                   }
               }
           }
       }
       return availableTheatre;
   }

    public List<Show> getShows(Movie movie, LocalDate date, Theatre theatre) {
        List<Show> result = new ArrayList<>();

        for (Screen screen : theatre.getScreen()) {
            for (Show show : screen.getShows(date)) {
                if (show.getMovie().equals(movie)) {
                    result.add(show);
                }
            }
        }
        return result;
    }
}
