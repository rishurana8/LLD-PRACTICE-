package org.rishudesign.com.chainofresponsibility;

public abstract class Handler {
    protected Handler next;

    public void setNext(Handler next){
        this.next = next;
    }

    public abstract void handleRequest(int amount);
}
