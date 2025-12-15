package com.example.integradoraSDD.model;

public class Loan {
    private int id;
    private int userId;
    private int bookId;
    private String status; // "ACTIVE" o "RETURNED"
    private String loanDate;
    private String returnDate; // null si no se ha devuelto


    public Loan(int id, int userId, int bookId, String loanDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.status = "ACTIVE";
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
