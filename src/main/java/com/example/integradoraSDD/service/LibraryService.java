package com.example.integradoraSDD.service;

import com.example.integradoraSDD.model.Book;
import com.example.integradoraSDD.model.HistoryAction;
import com.example.integradoraSDD.model.Loan;
import com.example.integradoraSDD.model.User;
import com.example.integradoraSDD.structures.SinglyLinkedList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// CLASES AUXILIARES NECESARIAS (Asumimos que existen y usan tus estructuras)
// 1. HistoryService (Que utiliza ArrayStack)
// 2. Loan (Que ahora usa String para fechas)

@Service
public class LibraryService {


    private SinglyLinkedList<Book> books = new SinglyLinkedList<>();
    private SinglyLinkedList<User> users = new SinglyLinkedList<>();
    private SinglyLinkedList<Loan> loans = new SinglyLinkedList<>();
    private int nextBookId = 6;
    private int nextUserId = 3;
    private int nextLoanId = 1;

    private HistoryService historyService = new HistoryService();


    private String getCurrentTimestamp() {
        return String.valueOf(System.currentTimeMillis()); // Usamos milisegundos como timestamp simple
    }


    public LibraryService() {

        books.add(new Book(1, "El nombre del viento", "Pathrik Rothfuss", "planeta", 15));
        books.add(new Book(2, "El temor de un hombre sabio", "Pathrik Rothfuss", "planeta", 12));
        books.add(new Book(3, "La música del silencio", "Pathrik Rothfuss", "planeta", 5));
        books.add(new Book(4, "El arbol del relampago", "Pathrik Rothfuss", "planeta", 1));
        books.add(new Book(5, "Las puertas de piedra", "Pathrik Rothfuss", "planeta", 10));

        users.add(new User(1, "Angel Chacon", "angelc@gmail.com"));
        users.add(new User(2, "Daniel Ayala", "daniela@gmail.com"));
    }


    //---------------- Métodos para buscar cosas ------------------

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

    //-------------------------CRUD-------------------------

    // Create
    public Book crearLibro(Book nuevoLibro){
        nuevoLibro.setId(nextBookId++);
        nuevoLibro.setActive(true);
        books.add(nuevoLibro);
        historyService.push(new HistoryAction(null, nuevoLibro.getId(), null, null, getCurrentTimestamp()));
        return nuevoLibro;
    }

    // Read All
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

    // Update
    public Book updateLibros(int id, Book newLibro){
        Book oldLibro = getBookById(id);

        if (oldLibro == null){
            throw new RuntimeException("No se puede actualizar, el libro no existe");
        }
        newLibro.setId(id);
        books.update(oldLibro, newLibro);
        return newLibro;
    }

    // Delete
    public void deleteLibro(int id){
        Book libroAEliminar = getBookById(id);
        if (libroAEliminar != null){
            books.removeLogic(libroAEliminar);
        }else{
            throw new RuntimeException("No se puede actualizar, el libro no existe");
        }
    }

    // -----------------------------------------------------------
    // 4. LÓGICA DE PRÉSTAMOS (MÉTODO CENTRAL)
    // -----------------------------------------------------------

    public Loan createLoanOrReservation(int userId, int bookId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);

        // ... (resto de las validaciones)
        if (user == null || book == null || !book.isActive()) return null;
        if (hasActiveLoanOrReservation(userId, bookId)) return null;

        if (book.getAvailableCopies() > 0) {
            // CREAR PRÉSTAMO
            int previousCopies = book.getAvailableCopies();
            book.decreaseAvailableCopies();

            int loanId = nextLoanId++;
            String timestamp = getCurrentTimestamp();
            Loan newLoan = new Loan(loanId, userId, bookId, timestamp);
            loans.add(newLoan); // Agrega a la lista de préstamos

            HistoryAction action = new HistoryAction(userId, bookId, loanId, previousCopies, timestamp);
            historyService.push(action);
            return newLoan;
        } else {
            // CREAR RESERVACIÓN
            book.addToWaitlist(userId);
            String timestamp = getCurrentTimestamp();
            HistoryAction action = new HistoryAction(userId, bookId, timestamp);
            historyService.push(action);
            return null;
        }
    }

    // Método auxiliar de validación (usando getLoanById y getBookById)
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

        // 2. Buscar en Lista de Espera: Esta validación es compleja sin un método 'contains' en ArrayQueue.
        // Por ahora, nos quedamos solo con la validación de préstamos activos.
        return false;
    }
}