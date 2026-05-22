package org.rishudesign.com.chainofresponsibility;

public class Director extends Handler{
    @Override
    public void handleRequest(int amount){
        if(amount<=10000){
            System.out.println("This loan is approved by director");
        }else{
            System.out.println("this amount cannot be approved ");
        }
    }
}
