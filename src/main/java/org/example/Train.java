package org.example;

import org.example.db.DatabaseHelper;
import org.example.model.Ticket;

import java.util.ArrayList;

public class Train {
    private final ArrayList<Ticket> confirmedTickets = new ArrayList<>();
    private final ArrayList<Ticket> racTickets = new ArrayList<>();
    private final ArrayList<Ticket> waitingListTickets = new ArrayList<>();
    private final Berth berth = new Berth();
    private final DatabaseHelper dbHelper = new DatabaseHelper();

    public void bookTicket(Ticket ticket) {
        if (confirmedTickets.size() < 2) {
            berth.allocateBerth(ticket);
            confirmedTickets.add(ticket);
        } else if (racTickets.isEmpty()) {
            ticket.setConfirmationStatus("In RAC");
            racTickets.add(ticket);
        } else if (waitingListTickets.isEmpty()) {
            ticket.setConfirmationStatus("In Waiting List");
            waitingListTickets.add(ticket);
        } else {
            System.out.println("No Tickets Available.");
            return;
        }
        dbHelper.saveTicket(ticket);
        displayAllTickets();
    }

    public void displayAllTickets() {
        ArrayList<Ticket> allTickets = dbHelper.fetchAllTickets();
        System.out.println("\n--- All Tickets ---");
        for (Ticket t : allTickets) {
            System.out.println("Ticket ID: " + t.getTicketId());
            System.out.println("Name: " + t.getName());
            System.out.println("Age: " + t.getAge());
            System.out.println("Gender: " + t.getGender());
            System.out.println("Berth Preference: " + t.getBerthPreference());
            System.out.println("Confirmation Status: " + t.getConfirmationStatus());
            System.out.println("------");
        }
    }

    public void cancelTicket(String ticketId) {
        Ticket ticket = dbHelper.fetchTicket(ticketId);
        if (ticket == null) {
            System.out.println("No matching ticket found in the database.");
            return;
        }

        if (removeTicket(ticket, confirmedTickets)) {
            promoteTicket(racTickets, confirmedTickets);
            promoteTicket(waitingListTickets, racTickets);
        } else if (removeTicket(ticket, racTickets)) {
            promoteTicket(waitingListTickets, racTickets);
        } else if (removeTicket(ticket, waitingListTickets)) {
            System.out.println("Ticket canceled successfully from Waiting List.");
        }
        dbHelper.deleteTicket(ticketId);
    }

    private boolean removeTicket(Ticket ticket, ArrayList<Ticket> ticketList) {
        return ticketList.removeIf(t -> t.getName().equals(ticket.getName()) && t.getAge() == ticket.getAge());
    }

    private void promoteTicket(ArrayList<Ticket> fromList, ArrayList<Ticket> toList) {
        if (!fromList.isEmpty()) {
            Ticket ticket = fromList.remove(0);
            ticket.setConfirmationStatus(toList == confirmedTickets ? "Confirmed" : "In RAC");
            toList.add(ticket);
            dbHelper.saveTicket(ticket);
        }
    }
}