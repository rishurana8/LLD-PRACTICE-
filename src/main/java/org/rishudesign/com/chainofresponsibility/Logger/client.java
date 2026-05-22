package org.rishudesign.com.chainofresponsibility.Logger;

public class client {
    public static void main(String args[]) {
        Logger infologger = new InfoLogger();
        Logger debuglogger = new Debug();
        Logger error = new Error();

        infologger.setNext(debuglogger);
        debuglogger.setNext(error);

        Logger log = infologger;
        log.log("ERROR","This is an error message");
    }

}
