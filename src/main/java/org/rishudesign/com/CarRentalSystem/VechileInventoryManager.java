package org.rishudesign.com.CarRentalSystem;
import org.rishudesign.com.CarRentalSystem.enums.VechileType;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class VechileInventoryManager {
    private Map<Vechile,List<Booking>> bookings = new ConcurrentHashMap<>();
    private Map<Vechile, ReentrantLock> locks  = new ConcurrentHashMap<>();

    public VechileInventoryManager(List<Vechile> availablevechiles){  //here in this constructor we have list of avaialble vechiles
        for(Vechile vechile : availablevechiles){
            bookings.put(vechile,new ArrayList<>());
            locks.put(vechile,new ReentrantLock());
        }

    }

    public Booking bookVechile(VechileType type,LocalDate from , LocalDate to,User user){
        for(Vechile v: bookings.keySet()){
            if (v.getVechileType() != type) continue;

            ReentrantLock lock = locks.get(v);
            lock.lock();

            try{
                if(isAvailable(v,from,to)){
                    Booking booking = new Booking(generateId(),from,to,user,v);
                    bookings.get(v).add(booking);
                    return booking;
                }
            }catch(Exception e){

            }finally{
                lock.unlock();
            }
        }
        return null;
    }

    private boolean isAvailable(Vechile v , LocalDate from , LocalDate to){
        for(Booking b  : bookings.get(v)){
            if(overlaps(b.getFrom(),b.getTo(),from,to)){
                return false;
            }
        }
        return true;
    }

    private boolean overlaps(LocalDate from1 , LocalDate to1 , LocalDate from2 , LocalDate to2){
       return !(to1.isBefore(from2) || to2.isBefore(from1));
    }

    private int generateId(){
        return new Random().nextInt(100000);
    }

}
