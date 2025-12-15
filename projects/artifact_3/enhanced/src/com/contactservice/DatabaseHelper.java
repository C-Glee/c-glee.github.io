package com.contactservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:contacts.db";

    // Call this once on app start
    public static void initDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS contacts ("
                   + "contactID TEXT PRIMARY KEY,"
                   + "firstName TEXT NOT NULL,"
                   + "lastName TEXT NOT NULL,"
                   + "phoneNum TEXT NOT NULL,"
                   + "address TEXT NOT NULL"
                   + ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Load all contacts from DB
    public static List<Contact> loadAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT contactID, firstName, lastName, phoneNum, address FROM contacts";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("contactID");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String phone = rs.getString("phoneNum");
                String addr = rs.getString("address");
                // Use your Contact constructor: (String contactID, String firstName, String lastName, String phoneNum, String address)
                Contact c = new Contact(id, first, last, phone, addr);
                contacts.add(c);
            }
        }
        return contacts;
    }

    // Insert a contact (throws SQLException if primary key conflict)
    public static void insertContact(Contact c) throws SQLException {
        String sql = "INSERT INTO contacts(contactID, firstName, lastName, phoneNum, address) VALUES (?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getContactID());
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getPhoneNum());
            ps.setString(5, c.getAddress());
            ps.executeUpdate();
        }
    }

    // Update an existing contact (based on contactID)
    public static void updateContact(Contact c) throws SQLException {
        String sql = "UPDATE contacts SET firstName=?, lastName=?, phoneNum=?, address=? WHERE contactID=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPhoneNum());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getContactID());
            ps.executeUpdate();
        }
    }

    // Delete by ID
    public static void deleteContact(String contactID) throws SQLException {
        String sql = "DELETE FROM contacts WHERE contactID=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, contactID);
            ps.executeUpdate();
        }
    }
}