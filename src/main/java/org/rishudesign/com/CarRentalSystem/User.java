package org.rishudesign.com.CarRentalSystem;

public class User {
    int userId;
    String name;

    public User(int userId,String name){
        this.userId = userId;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
