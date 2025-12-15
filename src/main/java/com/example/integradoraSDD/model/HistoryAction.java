package com.example.integradoraSDD.model;

public class HistoryAction {


    public enum ActionType {
        CREATE_LOAN,
        ADD_TO_WAITLIST,
        POP_WAITLIST,
        RETURN_LOAN,
        CREATE_BOOK
    }

    private String actionType;
    private Integer userId;
    private Integer bookId;
    private Integer loanId;
    private Integer previousAvailableCopies;
    private String timestamp;


    public HistoryAction(Integer userId, Integer bookId, Integer loanId, Integer previousAvailableCopies, String timestamp) {
        this.actionType = ActionType.CREATE_LOAN.toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
        this.previousAvailableCopies = previousAvailableCopies;
        this.timestamp = timestamp;
    }

    public HistoryAction(Integer userId, Integer bookId, String timestamp) {
        this.actionType = ActionType.ADD_TO_WAITLIST.toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = null;
        this.previousAvailableCopies = null;
        this.timestamp = timestamp;
    }

    public HistoryAction(Integer userId, Integer bookId, Integer loanId, Integer previousAvailableCopies, String timestamp, String customActionType) {
        this.actionType = customActionType;
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
        this.previousAvailableCopies = previousAvailableCopies;
        this.timestamp = timestamp;
    }


    public String getActionType() { return actionType; }
    public Integer getUserId() { return userId; }
    public Integer getBookId() { return bookId; }
    public Integer getLoanId() { return loanId; }
    public Integer getPreviousAvailableCopies() { return previousAvailableCopies; }
    public String getTimestamp() { return timestamp; }

}