package org.example.model;

public class Ticket {
    private final String ticketId;
    private final String name;
    private final int age;
    private final String gender;
    private final String berthPreference;
    private String confirmationStatus;

    public Ticket(String ticketId, String name, int age, String gender, String berthPreference) {
        this.ticketId = ticketId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
    }

    public Ticket(String ticketId, String name, int age, String gender, String berthPreference, String confirmationStatus) {
        this.ticketId = ticketId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
        this.confirmationStatus = confirmationStatus;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBerthPreference() {
        return berthPreference;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }
}