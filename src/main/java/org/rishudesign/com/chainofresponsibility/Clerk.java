package org.rishudesign.com.chainofresponsibility;

public class Clerk extends Handler{

    @Override
    public void handleRequest(int amount){
        if (amount <= 1000) {
            System.out.println("Your loan amount is approved");
        }else if(next!=null){
            next.handleRequest(amount);
        }
    }
}
