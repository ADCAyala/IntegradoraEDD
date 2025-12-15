package com.example.integradoraSDD.model;

public class HistoryAction {

    // DEFINICIÓN DE ACCIONES: ¡Agregaremos las que usa LibraryService!
    public enum ActionType {
        CREATE_LOAN,
        ADD_TO_WAITLIST,
        POP_WAITLIST, // Usado en returnLoan para activar la cola
        RETURN_LOAN, // Usado en returnLoan para registrar la devolución
        CREATE_BOOK  // Usado en crearLibro
    }

    private String actionType; // Campo tipo String (Almacena el String del Enum)
    private Integer userId;
    private Integer bookId;
    private Integer loanId;
    private Integer previousAvailableCopies;
    private String timestamp;

    // CONSTRUCTOR 1: Para acciones de CREATE_LOAN (Requiere todos los datos)
    public HistoryAction(Integer userId, Integer bookId, Integer loanId, Integer previousAvailableCopies, String timestamp) {
        this.actionType = ActionType.CREATE_LOAN.toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
        this.previousAvailableCopies = previousAvailableCopies;
        this.timestamp = timestamp;
    }

    // CONSTRUCTOR 2: Para acciones de ADD_TO_WAITLIST (Requiere UserID, BookID, Timestamp)
    public HistoryAction(Integer userId, Integer bookId, String timestamp) {
        this.actionType = ActionType.ADD_TO_WAITLIST.toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = null;
        this.previousAvailableCopies = null;
        this.timestamp = timestamp;
    }

    // CONSTRUCTOR 3: Para acciones de RETURN_LOAN / POP_WAITLIST / CREATE_BOOK (Si necesitas flexibilidad)
    // Usaremos este para simplificar el código del service
    public HistoryAction(Integer userId, Integer bookId, Integer loanId, Integer previousAvailableCopies, String timestamp, String customActionType) {
        this.actionType = customActionType;
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
        this.previousAvailableCopies = previousAvailableCopies;
        this.timestamp = timestamp;
    }

    // --- GETTERS & SETTERS (Asegúrate de que existan) ---
    public String getActionType() { return actionType; }
    public Integer getUserId() { return userId; }
    public Integer getBookId() { return bookId; }
    public Integer getLoanId() { return loanId; }
    public Integer getPreviousAvailableCopies() { return previousAvailableCopies; }
    public String getTimestamp() { return timestamp; }
    // ... otros getters y setters ...
}