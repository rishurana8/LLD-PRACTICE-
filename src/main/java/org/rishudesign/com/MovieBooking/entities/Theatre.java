package org.rishudesign.com.MovieBooking.entities;
import java.util.*;

import org.rishudesign.com.MovieBooking.enums.City;

public class Theatre {
    private int id;
    private City city;
    private String name;
    List<Screen> screens;

    public Theatre(String name, City city, List<Screen> screens) {
        this.name = name;
        this.city = city;
        this.screens = screens;
    }

    public City getCity(){
        return this.city;
    }

   public List<Screen> getScreen(){
        return this.screens;
   }

}
