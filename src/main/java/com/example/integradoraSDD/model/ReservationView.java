package com.example.integradoraSDD.model;

public class ReservationView {
    private int position;
    private int userId;
    private String userName;

    // Constructor
    public ReservationView(int position, int userId, String userName) {
        this.position = position;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters...
    public int getPosition() { return position; }
    public int getUserId() { return userId; }
    public String getUserName() { return userName; }
}