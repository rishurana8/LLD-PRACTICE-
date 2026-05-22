package org.rishudesign.com.chainofresponsibility;

public class Client {
    public static void main(String args[]) {


    Handler clerk = new Clerk();
    Handler Director = new Director();
    Handler Manager = new Manager();


    clerk.setNext(Manager);
    Manager.setNext(Director);

    clerk.handleRequest(8000);
 }

}
