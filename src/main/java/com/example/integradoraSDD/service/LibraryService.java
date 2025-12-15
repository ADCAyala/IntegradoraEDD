package com.example.integradoraSDD.service;

import com.example.integradoraSDD.model.Book;
import com.example.integradoraSDD.model.HistoryAction;
import com.example.integradoraSDD.model.Loan;
import com.example.integradoraSDD.model.User;
import com.example.integradoraSDD.structures.SinglyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class LibraryService {


    private SinglyLinkedList<Book> books = new SinglyLinkedList<>();
    private SinglyLinkedList<User> users = new SinglyLinkedList<>();
    private SinglyLinkedList<Loan> loans = new SinglyLinkedList<>();

    private int nextBookId = 6;
    private int nextUserId = 3;
    private int nextLoanId = 1;

    @Autowired
    private HistoryService historyService;



    private String getCurrentTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }


    public LibraryService() {

        books.add(new Book(1, "El nombre del viento", "Pathrik Rothfuss", "fantasia", 15));
        books.add(new Book(2, "El temor de un hombre sabio", "Pathrik Rothfuss", "fantasia", 12));
        books.add(new Book(3, "La música del silencio", "Pathrik Rothfuss", "fantasia", 5));
        books.add(new Book(4, "El arbol del relampago", "Pathrik Rothfuss", "fantasia", 1));
        books.add(new Book(5, "Las puertas de piedra", "Pathrik Rothfuss", "fantasia", 10));

        users.add(new User(1, "Angel Chacon", "angelc@gmail.com"));
        users.add(new User(2, "Daniel Ayala", "daniela@gmail.com"));
        users.add(new User(3, "Getse Cruz", "getsec@gmail.com"));
    }


    // ------------------- MÉTODOS --------------------------------

    public Book getBookById(int id) {
        var current = books.getHead();
        while(current != null){
            Book libroActual = (Book) current.data;
            if(libroActual.getId() == id){
                return libroActual;
            }
            current = current.next;
        }
        return null;
    }

    public User getUserById(int id) {
        var current = users.getHead();
        while(current != null){
            User userActual = (User) current.data;
            if(userActual.getId() == id){
                return userActual;
            }
            current = current.next;
        }
        return null;
    }

    public Loan getLoanById(int id) {
        var current = loans.getHead();
        while(current != null){
            Loan loanActual = (Loan) current.data;
            if(loanActual.getId() == id){
                return loanActual;
            }
            current = current.next;
        }
        return null;
    }

    // ------------------------- CRUD -----------------------------

    public Book crearLibro(Book nuevoLibro){
        nuevoLibro.setId(nextBookId++);
        nuevoLibro.setActive(true);
        books.add(nuevoLibro);
        historyService.push(new HistoryAction(null, nuevoLibro.getId(), null, null, getCurrentTimestamp(), "CREATE_BOOK"));
        return nuevoLibro;
    }

    public User crearUsuario(User nuevoUsuario) {
        nuevoUsuario.setId(nextUserId++);
        users.add(nuevoUsuario);
        return nuevoUsuario;
    }

    public List<User> getAllUsers(){
        List<User> listaSalida = new ArrayList<>();
        var current = users.getHead();
        while (current != null){
            User user = (User) current.data;
            listaSalida.add(user);
            current = current.next;
        }
        return listaSalida;
    }

    public List<Book> getAllLibros(){
        List<Book> listaSalida = new ArrayList<>();
        var current = books.getHead();
        while (current != null){
            Book libro = (Book) current.data;
            listaSalida.add(libro);
            current = current.next;
        }
        return listaSalida;
    }

    public Book getByID(int id){
        return getBookById(id);
    }

    public Book updateLibros(int id, Book newLibro){
        Book oldLibro = getBookById(id);

        if (oldLibro == null){
            throw new RuntimeException("No se puede actualizar, el libro no existe");
        }
        newLibro.setId(id);
        books.update(oldLibro, newLibro);
        return newLibro;
    }

    public void deleteLibro(int id){
        Book libroAEliminar = getBookById(id);

        if (libroAEliminar != null){
            libroAEliminar.setActive(false);
        } else {
            throw new RuntimeException("No se puede dar de baja, el libro no existe.");
        }
    }


    // ------------------- MÉTODOS DE PRÉSTAMO --------------------

    private boolean hasActiveLoanOrReservation(int userId, int bookId) {
        // 1. Buscar Préstamo Activo
        var current = loans.getHead();
        while(current != null){
            Loan loan = (Loan) current.data;
            if (loan.getUserId() == userId &&
                    loan.getBookId() == bookId &&
                    loan.getStatus().equals("ACTIVE")) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    public Loan createLoanOrReservation(int userId, int bookId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);

        if (user == null || book == null || !book.isActive()) return null;
        if (hasActiveLoanOrReservation(userId, bookId)) return null; // Llama al método

        if (book.getAvailableCopies() > 0) {
            int previousCopies = book.getAvailableCopies();
            book.decreaseAvailableCopies();

            int loanId = nextLoanId++;
            String timestamp = getCurrentTimestamp();
            Loan newLoan = new Loan(loanId, userId, bookId, timestamp);
            loans.add(newLoan);

            historyService.push(new HistoryAction(userId, bookId, loanId, previousCopies, timestamp));
            return newLoan;
        } else {
            book.addToWaitlist(userId);
            String timestamp = getCurrentTimestamp();
            historyService.push(new HistoryAction(userId, bookId, timestamp));
            return null;
        }
    }

    public Loan returnLoan(int loanId) {

        Loan loan = getLoanById(loanId);
        if (loan == null || "RETURNED".equals(loan.getStatus())) return null;

        String fechaActual = getCurrentTimestamp();
        loan.setStatus("RETURNED");
        loan.setReturnDate(fechaActual);

        Book book = getBookById(loan.getBookId());
        if (book == null) return loan;

        if (!book.getWaitlist().isEmpty()) {

            Integer nextUserId = book.getWaitlist().dequeue();

            if (nextUserId != null) {
                historyService.push(new HistoryAction(nextUserId, book.getId(), null, null, fechaActual, "POP_WAITLIST"));

                int newLoanId = nextLoanId++;
                Loan newLoan = new Loan(newLoanId, nextUserId, book.getId(), fechaActual);
                loans.add(newLoan);

                historyService.push(new HistoryAction(nextUserId, book.getId(), newLoanId, 0, fechaActual, "CREATE_LOAN"));
            }

        } else {
            int prevCopies = book.getAvailableCopies();
            book.setAvailableCopies(prevCopies + 1);
            historyService.push(new HistoryAction(loan.getUserId(), book.getId(), loan.getId(), book.getAvailableCopies(), fechaActual, "RETURN_LOAN"));
        }

        return loan;
    }


    public List<Integer> getWaitlistForBook(int bookId) {
        Book book = getBookById(bookId);
        if (book == null || !book.isActive()) {
            return null;
        }
        return book.getWaitlist().toList();
    }


    public String cancelReservation(int userId, int bookId) {
        Book book = getBookById(bookId);

        if (book == null || !book.isActive()) {
            return "Error: Libro ID " + bookId + " no encontrado o inactivo.";
        }

        boolean removed = book.getWaitlist().removeByValue(userId);

        if (removed) {
            return "Reserva cancelada exitosamente para el Usuario ID " + userId + " en el Libro ID " + bookId + ".";
        } else {
            return "Error: El Usuario ID " + userId + " no estaba en la lista de espera del Libro ID " + bookId + ".";
        }
    }

    public String undoLastAction() {

        if (historyService.isEmpty()) {
            return "Error: No hay acciones recientes para deshacer.";
        }

        HistoryAction lastAction = historyService.pop();

        if (lastAction == null) {
            return "Error: No se pudo obtener la última acción de la pila.";
        }

        String actionType = lastAction.getActionType();
        Integer bookId = lastAction.getBookId();
        Book book = (bookId != null) ? getBookById(bookId) : null;

        if ("CREATE_LOAN".equals(actionType)) {
            Integer loanId = lastAction.getLoanId();
            Integer prevCopies = lastAction.getPreviousAvailableCopies();

            if (loanId == null || book == null || prevCopies == null) {
                return "Error crítico al deshacer CREATE_LOAN: Faltan datos clave para reversión.";
            }

            Loan loanToRemove = getLoanById(loanId);
            if (loanToRemove != null) {
                loans.removeAll(loanToRemove);
            }
            book.setAvailableCopies(book.getAvailableCopies() + 1);

            return "UNDO Exitoso: Préstamo (ID: " + loanId + ") revertido. Stock de " + book.getTitle() + " restaurado a " + book.getAvailableCopies();

        } else if ("ADD_TO_WAITLIST".equals(actionType)) {
            Integer userId = lastAction.getUserId();

            if (book == null || userId == null) {
                return "Error crítico al deshacer ADD_TO_WAITLIST: Faltan datos clave.";
            }


            boolean removed = book.getWaitlist().removeByValue(userId);

            if (removed) {
                return "UNDO Exitoso: Usuario " + userId + " removido de la lista de espera de " + book.getTitle() + ".";
            } else {
                return "Advertencia: Usuario " + userId + " ya no estaba en la lista de espera (no se pudo revertir).";
            }
        } else if ("POP_WAITLIST".equals(actionType)) {
            Integer userId = lastAction.getUserId();
            if (book == null || userId == null) {
                return "Error crítico al deshacer POP_WAITLIST: Faltan datos clave.";
            }

            book.getWaitlist().enqueue(userId);

            return "UNDO Exitoso: Usuario " + userId + " re-agregado a la lista de espera de " + book.getTitle() + ".";

        } else if ("RETURN_LOAN".equals(actionType)) {
            Integer loanId = lastAction.getLoanId();
            Loan loanToRevert = getLoanById(loanId);

            if (loanToRevert == null || book == null) {
                return "Error crítico al deshacer RETURN_LOAN: Faltan datos clave para reversión.";
            }

            loanToRevert.setStatus("ACTIVE");
            loanToRevert.setReturnDate(null);

            book.setAvailableCopies(book.getAvailableCopies() - 1);

            return "UNDO Exitoso: Devolución del Préstamo (ID: " + loanId + ") revertida a ACTIVE. Stock de " + book.getTitle() + " disminuido.";
        } else {
            return "Advertencia: Acción desconocida (" + actionType + ") encontrada en la pila. No se pudo deshacer.";
        }
    }


    public List<Loan> getAllLoans() {
        return this.loans.toList();
    }


    public List<Loan> getActiveLoans() {
        List<Loan> activeLoans = new ArrayList<>();
        List<Loan> allLoans = loans.toList();
        for (Loan loan : allLoans) {
            if (!loan.isReturned()) {
                activeLoans.add(loan);
            }

        }

        return activeLoans;
    }



    public List<Loan> getLoansByUserId(int userId) {
        List<Loan> userLoans = new ArrayList<>();
        List<Loan> allLoans = loans.toList();

        for (Loan loan : allLoans) {

            if (loan.getUserId() == userId) {
                userLoans.add(loan);
            }
        }

        return userLoans;
    }
    public List<HistoryAction> getHistory() {
        return historyService.getHistory();
    }
}