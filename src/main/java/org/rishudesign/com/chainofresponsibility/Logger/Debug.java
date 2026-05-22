package org.rishudesign.com.chainofresponsibility.Logger;

public class Debug extends Logger{
    public boolean canHandle(String level){
        if(level.equals("DEBUG"))
            return true;
        return false;
    }
    public void write(String msg){
        System.out.println("DEBUG :"  + msg);
    }
}
