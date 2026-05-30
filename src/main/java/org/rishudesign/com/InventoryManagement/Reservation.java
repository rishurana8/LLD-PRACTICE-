package org.rishudesign.com.InventoryManagement;
import java.util.*;

public class Reservation {
    private String id;
    private Map<Integer,Integer> reservedItems;
    private ReservationStatus status;

    public Reservation(String id, ReservationStatus status,Map<Integer,Integer>reservedItems){
        this.id = id;
        this.reservedItems = reservedItems;
        this.status = status;
    }

    public String getReservationId() {
        return id;
    }

    public Map<Integer, Integer> getReservedItems() {
        return reservedItems;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

}
