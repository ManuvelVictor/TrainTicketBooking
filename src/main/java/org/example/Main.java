package org.example;

import org.example.model.Ticket;
import org.example.utils.Utils;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Train train = new Train();

    public static void main(String[] args) {
        boolean end = false;

        while (!end) {
            System.out.println("\n--- Railway Ticket Booking System ---");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. View All Tickets");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Ticket newTicket = generateTicket();
                    train.bookTicket(newTicket);
                    break;
                case 2:
                    System.out.print("Enter the Ticket ID to cancel: ");
                    String ticketId = scanner.next();
                    train.cancelTicket(ticketId);
                    break;
                case 3:
                    train.displayAllTickets();
                    break;
                case 4:
                    end = true;
                    System.out.println("Exiting the system. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static Ticket generateTicket() {
        System.out.println("\n--- Enter Ticket Details ---");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        System.out.print("Gender: ");
        String gender = scanner.next();
        System.out.print("Berth Preference (LB/MB/UB): ");
        String berthPref = scanner.next().toUpperCase();
        String ticketId = Utils.generateTicketId(name, age, berthPref);

        return new Ticket(ticketId, name, age, gender, berthPref);
    }
}