package org.rishudesign.com.chainofresponsibility.Logger;

public class Error extends Logger{
    public boolean canHandle(String level){
        if(level.equals("ERROR"))
            return true;
        return false;
    }
    public void write(String msg){
        System.out.println("ERROR: "+ msg);
    }
}
