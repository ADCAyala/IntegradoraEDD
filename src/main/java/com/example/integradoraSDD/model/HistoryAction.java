package com.example.integradoraSDD.model;

public class HistoryAction {
    // Usaremos un Enum para las acciones, es una buena práctica
    public enum ActionType {
        CREATE_LOAN,
        ADD_TO_WAITLIST
    }

    private String actionType; // Campo tipo String
    private Integer userId;
    private Integer bookId;
    private Integer loanId;
    private Integer previousAvailableCopies;
    private String timestamp;

    // Constructor para CREATE_LOAN
    public HistoryAction(Integer userId, Integer bookId, Integer loanId, Integer previousAvailableCopies, String timestamp) {
        // CORRECCIÓN 1: Convertir el Enum a String
        this.actionType = ActionType.CREATE_LOAN.toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
        this.previousAvailableCopies = previousAvailableCopies;
        this.timestamp = timestamp;
    }

    // Constructor para ADD_TO_WAITLIST
    public HistoryAction(Integer userId, Integer bookId, String timestamp) {
        // CORRECCIÓN 2: Convertir el Enum a String
        this.actionType = ActionType.ADD_TO_WAITLIST.toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = null;
        this.previousAvailableCopies = null;
        this.timestamp = timestamp;
    }

    // Getters y Setters (Asegúrate de que existan todos los setters que usa LibraryService)
    public String getActionType() {
        return actionType; // Devuelve String, no ActionType, para coincidir con el campo
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public Integer getPreviousAvailableCopies() {
        return previousAvailableCopies;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // ... Asegúrate de que los setters para actionType, userId, bookId, etc. existan...
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public void setPreviousAvailableCopies(Integer previousAvailableCopies) {
        this.previousAvailableCopies = previousAvailableCopies;
    }


    // ...
}