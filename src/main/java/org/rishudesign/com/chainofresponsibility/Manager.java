package org.rishudesign.com.chainofresponsibility;

public class Manager extends Handler{

    @Override
    public void handleRequest(int amount){
        if(amount<=3000){
            System.out.println("Your loan has been approved by manager");
        }else if(next!=null){
            next.handleRequest(amount);
        }
    }
}
