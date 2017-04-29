package com.company;

import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;

    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    protected static int ticketIdCounter = 1;

    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    protected Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = ticketIdCounter;
        ticketIdCounter++;
    }

    protected int getPriority() {
        return priority;
    }
    protected int getTicketID() {
        return ticketID;
    }
    protected String getTicketDescription() {
        return description;
    }

    public String toString(){
        return("ID: " + this.ticketID + " Issue: " + this.description + " Priority: " +  this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
}

