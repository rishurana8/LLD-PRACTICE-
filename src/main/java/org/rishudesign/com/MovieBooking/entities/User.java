package org.rishudesign.com.MovieBooking.entities;

public class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
