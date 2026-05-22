package org.rishudesign.com.MovieBooking.entities;

import org.rishudesign.com.MovieBooking.enums.PaymentStatus;

import java.util.UUID;

public class Payment {
    private UUID id;
    private PaymentStatus status;

    public Payment( PaymentStatus status){

        this.status = status;
    }

    public PaymentStatus getStatus(){
        return this.status;
    }
}
