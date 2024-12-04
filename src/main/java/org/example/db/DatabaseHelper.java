package org.example.db;

import org.example.model.Ticket;
import org.example.utils.Utils;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:tickets.db";
    private static final String TABLE_NAME = "Tickets";

    public DatabaseHelper() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "ticketId TEXT PRIMARY KEY, "
                + "name TEXT NOT NULL, "
                + "age INTEGER NOT NULL, "
                + "gender TEXT NOT NULL, "
                + "berthPreference TEXT NOT NULL, "
                + "confirmationStatus TEXT NOT NULL"
                + ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void saveTicket(Ticket ticket) {
        String ticketId = Utils.generateTicketId(ticket.getName(), ticket.getAge(), ticket.getBerthPreference());

        String sql = "INSERT INTO " + TABLE_NAME + " (ticketId, name, age, gender, berthPreference, confirmationStatus) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketId); // Save the generated ticket ID
            pstmt.setString(2, ticket.getName());
            pstmt.setInt(3, ticket.getAge());
            pstmt.setString(4, ticket.getGender());
            pstmt.setString(5, ticket.getBerthPreference());
            pstmt.setString(6, ticket.getConfirmationStatus());
            pstmt.executeUpdate();
            System.out.println("Ticket saved with ID: " + ticketId);
        } catch (SQLException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
        }
    }

    public Ticket fetchTicket(String ticketId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ticketId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ticket(
                        rs.getString("ticketId"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("berthPreference"),
                        rs.getString("confirmationStatus")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching ticket: " + e.getMessage());
        }
        return null;
    }

    public void deleteTicket(String ticketId) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ticketId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketId);
            pstmt.executeUpdate();
            System.out.println("Ticket with ID " + ticketId + " has been deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting ticket: " + e.getMessage());
        }
    }

    public ArrayList<Ticket> fetchAllTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getString("ticketId"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("berthPreference"),
                        rs.getString("confirmationStatus")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all tickets: " + e.getMessage());
        }
        return tickets;
    }
}