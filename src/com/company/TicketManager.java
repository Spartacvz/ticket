package com.company;

import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class TicketManager {

    // Creates list to hold the tickets
    private LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
    private LinkedList<Ticket> resolvedTicketQue = new LinkedList<Ticket>();
    // Accepts user input for ticket search
    private Scanner SBI = new Scanner(System.in);
    private Scanner DBI = new Scanner(System.in);
    Date date = new Date();


    private void mainMenu() throws IOException {
        // Creates a file writer for the storage of tickets
        FileWriter tickets = new FileWriter("Open_tickets.txt");
        BufferedWriter bufTickets = new BufferedWriter(tickets);
        FileWriter resTickets = new FileWriter("Resolved_tickets_" + (date.toString()) + ".txt");
        BufferedWriter bufResTickets = new BufferedWriter(resTickets);

        FileReader resTick = new FileReader("Resolved_tickets_" + (date.toString()) + ".txt");
        BufferedReader bufResTick = new BufferedReader(resTick);


        while (true) {
            // Displays main menu
            System.out.println("1. Enter Ticket\n2. Delete Ticket by ID\n3. Display All Tickets\n4. Delete by issue\n5. Search by issue\n6. Save tickets to file\n7. Quit");
            // Prompts user to select an action
            int task = Input.getPositiveIntInput("Enter your selection");

            if (task == 1) {    // Give the option to add tickets if user selects "1"
                addTickets();
            }
            else if (task == 2) {   // Gives option to delete tickets by searching ID if user selects "2"
                deleteTicketById();
            }
            else if (task == 3) {   // Gives option to print all tickets if user selects "3"
                printAllTickets();
            }
            else if (task == 4) {   // Gives option to delete ticket by searching the issue if user selects "4"
                deleteTicketByIssue();
            }
            else if (task == 5) {
                searchByIssue();
            }
            else if (task == 6) {
                bufTickets.write(String.valueOf(ticketQueue));
                bufTickets.close();
            }
            else if (task == 7) {
                System.out.println("Quitting program and saving tickets to files");
                bufTickets.write(String.valueOf(ticketQueue));
                bufTickets.close();
                bufResTickets.write(String.valueOf(resolvedTicketQue));
                bufResTickets.close();
                break;
            }
            else {
                //this will happen for 3 or any other selection that is a valid int
                //Default will be print all tickets
                printAllTickets();
            }
        }
    }

    private void addTickets() {

        while (true) {

            Date dateReported = new Date(); //Default constructor creates Date with current date/time

            String description = Input.getStringInput("Enter problem");
            String reporter = Input.getStringInput("Who reported this issue?");
            int priority = Input.getPositiveIntInput("Enter priority of " + description);

            Ticket t = new Ticket(description, priority, reporter, dateReported);
            //ticketQueue.add(t);
            addTicketInPriorityOrder(t);

            printAllTickets();

            String more = Input.getStringInput("More tickets to add? Enter N for no, anything else to add more tickets");

            if (more.equalsIgnoreCase("N")) {
                return;
            }
        }
    }

    private void deleteTicketById() {

        printAllTickets();   //display list for user

        try {
            if (ticketQueue.size() == 0) {    //no tickets!
                System.out.println("No tickets to delete!\n");
                return;
            }

            int deleteID = Input.getPositiveIntInput("Enter ID of ticket to delete");

            //Loop over all tickets. Delete the one with this ticket ID
            boolean found = false;
            for (Ticket ticket : ticketQueue) {
                if (ticket.getTicketID() == deleteID) {
                    found = true;
                    ticketQueue.remove(ticket);
                    ResolvedTicket resolvedTicket = (ResolvedTicket) ticket;
                    resolvedTicketQue.add(resolvedTicket);
                    System.out.println(String.format("Ticket %d resolved", deleteID));
                    break; //don't need the loop any more.
                }
            }
            if (!found) {
                System.out.println("Ticket ID not found, no ticket deleted. Please try again.");
                deleteID = Input.getPositiveIntInput("Enter ID of ticket to delete");

                //Loop over all tickets. Delete the one with this ticket ID
                for (Ticket ticket : ticketQueue) {
                    if (ticket.getTicketID() == deleteID) {
                        found = true;
                        ticketQueue.remove(ticket);
                        System.out.println(String.format("Ticket %d deleted", deleteID));
                        break; //don't need the loop any more.
                    }
                }
            }
            printAllTickets();  //print updated list
        }
        catch (Exception e) {
            System.out.println("Sorry, that entry was invalid, please try again.");
            deleteTicketById();
        }

    }

    private void printAllTickets() {
        System.out.println("---------- All open tickets ----------");

        for (Ticket t : ticketQueue ) {
            System.out.println(t); // This calls the  toString method for the Ticket object.
        }
        System.out.println("---------- End of ticket list ----------");

    }

    private void deleteTicketByIssue() {

        try {
            if (ticketQueue.size() == 0) {    //no tickets!
                System.out.println("No tickets to delete!\n");
                return;
            }
            // Ask user for string to search for
            searchByIssue();
            // Ask for ID of ticket to delete
            System.out.println("I've found the following issues matching your search term, enter the number of the ticket you'd like to delete.");
            int requested = DBI.nextInt();
            // Delete that ticket
            for (Ticket ticket : ticketQueue) {
                if (ticket.getTicketID() == requested) {
                    ticketQueue.remove(ticket);
                    ResolvedTicket resolvedTicket = (ResolvedTicket) ticket;
                    resolvedTicketQue.add(resolvedTicket);
                    System.out.println(String.format("Ticket %d resolved", requested));
                    break; //don't need the loop any more.
                }
            }
        } catch (Exception e) {
            System.out.println("The following issues match your term, enter the number of the ticket you'd like to delete.");
            int requested = DBI.nextInt();
            // Delete that ticket
            for (Ticket ticket : ticketQueue) {
                int deleteTicket = ticket.getTicketID();    // Define a variable to represent ticket in list
                if (requested == deleteTicket) {    // Compare ticket from list to requested ticket (if tickets are same...)
                    ticketQueue.remove(deleteTicket);   // remove ticket from que
                    System.out.println("You have successfully deleted Ticket ID: " + deleteTicket);
                }
            }
        }
    }

    private void searchByIssue() {
        // Ask user for search term
        System.out.println("Type in a search term on the line below");
        String searched = SBI.next();
        String searchIssue = searched.toLowerCase();    // The search should be case-insensitive
        for (Ticket ticket : ticketQueue) {    // Cycles through all tickets in que
            String issue = ticket.getTicketDescription();
            if (issue.contains(searchIssue))    {     // Determine whether the search issue is contained in the ticket Que
                System.out.println("Ticket Number: " + ticket);
                System.out.println("Issue: " + issue);
            }
        }
    }

    private void addTicketInPriorityOrder(Ticket newTicket){
        //Logic: assume the list is either empty or sorted
        if (ticketQueue.size() == 0 ) {//Special case - if list is empty, add ticket and return
            ticketQueue.add(newTicket);
            return;
        }
        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end
        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < ticketQueue.size() ; x++) {    //use a regular for loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= ticketQueue.get(x).getPriority()) {
                ticketQueue.add(x, newTicket);
                return;
            }
        }
        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        ticketQueue.addLast(newTicket);
    }

    private void readFiles () {
        String fileName = "Open_tickets.txt";
        String line = null;
            try {
                FileReader tick = new FileReader("Open_tickets.txt");
                BufferedReader bufTick = new BufferedReader(tick);

                while ((line = bufTick.readLine()) != null) {
                    System.out.println(line);
                }
                bufTick.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to open file " + fileName);
            } catch (IOException ex) {
                System.out.println("Error reading file " + fileName);
            }


        String resolFileName = "Open_tickets.txt";
        String line2 = null;
        try {
            FileReader tick = new FileReader("Open_tickets.txt");
            BufferedReader bufTick = new BufferedReader(tick);

            while ((line2 = bufTick.readLine()) != null) {
                System.out.println(line);
            }
            bufTick.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + resolFileName);
        } catch (IOException ex) {
            System.out.println("Error reading file " + resolFileName);
        }
        }

    /* Main is hiding down here. Create a TicketManager object, and call the mainMenu method.
    Avoids having to make all of the methods in this class static. */
    public static void main(String[] args) throws IOException {
        TicketManager manager = new TicketManager();


        manager.mainMenu();
    }

}

