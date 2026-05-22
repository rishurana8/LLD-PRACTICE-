package org.rishudesign.com.chainofresponsibility.Logger;

public class InfoLogger extends Logger{
    public boolean canHandle(String level){
        if(level.equals("INFO"))
            return true;
        return false;
    }

    public void write(String msg){
        System.out.println("INFO :" + msg);
    }
}
