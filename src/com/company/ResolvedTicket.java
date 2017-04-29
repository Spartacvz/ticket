package com.company;

import java.util.Date;

public class ResolvedTicket extends Ticket {

    private Date resolveDate;
    private String resolution;
    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;

    protected static int ticketIdCounter = 1;
    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int resolvedTicketID;

    protected ResolvedTicket(String desc, int p, String rep, Date date, String res, Date resDate)  {
        super(desc, p, rep, date);

        this.resolution = res;
        this.resolveDate = resDate;
        this.resolvedTicketID = ticketIdCounter;
        ticketIdCounter++;
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
    }
    protected String getResolution() {
        return resolution;
    }
    protected Date getResolveDate() {
        return resolveDate;
    }
}
