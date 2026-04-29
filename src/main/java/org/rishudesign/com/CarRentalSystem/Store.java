package org.rishudesign.com.CarRentalSystem;
import java.time.LocalDate;
import java.util.*;
import org.rishudesign.com.CarRentalSystem.enums.VechileType;

public class Store {
    int id;
    Location location;
    private VechileInventoryManager vechileinventoryManager;

    public Store(int id, Location location, List<Vechile> availableVechiles){
        this.id= id;
        this.location = location;
        this.vechileinventoryManager = new VechileInventoryManager(availableVechiles);
    }

    public Location getLocation(){
        return this.location;
    }

   public Booking book(VechileType type, LocalDate from, LocalDate to,User user){
       return vechileinventoryManager.bookVechile(type,from,to,user);
   }

}
