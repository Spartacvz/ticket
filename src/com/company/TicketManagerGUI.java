package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dadub on 3/29/2017.
 */
public class TicketManagerGUI {
    private JPanel ticketManagerGUI;
    private JRadioButton EnterTicket;
    private JRadioButton DeleteTicketByID;
    private JRadioButton DisplayAllTickets;
    private JRadioButton Quit;

    public TicketManagerGUI() {
        EnterTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
