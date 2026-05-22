package org.rishudesign.com.chainofresponsibility.Logger;

public abstract class Logger {
    protected Logger next;

    public void setNext(Logger log){
        this.next  = log;
    }

    public void log(String level,String msg){
        if(canHandle(level)){
            write(msg);
        }else if(next!=null){
            next.log(level,msg);
        }
    }

    public abstract boolean canHandle(String level);
    public abstract void write(String msg);
}
