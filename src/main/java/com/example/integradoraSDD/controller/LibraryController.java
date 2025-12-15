package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.model.Loan;
import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LibraryController {

    // Inyecta tu LibraryService para poder usar sus métodos
    @Autowired
    private LibraryService libraryService;

    // Endpoint: POST /api/loans
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Map<String, Integer> payload) {

        Integer userId = payload.get("userId");
        Integer bookId = payload.get("bookId");

        if (userId == null || bookId == null) {
            return new ResponseEntity<>("Debe proporcionar userId y bookId.", HttpStatus.BAD_REQUEST);
        }

        Loan result = libraryService.createLoanOrReservation(userId, bookId);

        if (result != null) {
            // CASO A: Préstamo creado con éxito
            return new ResponseEntity<>(result, HttpStatus.CREATED); // 201 Created
        } else {
            // CASO B: Reserva o Error (Si result es null, la lógica del servicio manejó la reserva o el error)

            // CORRECCIÓN DE SINTAXIS: La palabra 'body:' debe ser eliminada
            return new ResponseEntity<>("Proceso finalizado. Revise la lista de espera o si ya tenía un préstamo activo.", HttpStatus.OK);
        }
    }

    // Endpoint: GET /api/loans
    @GetMapping // Esto mapea a la ruta base /api/loans
    public ResponseEntity<List<Loan>> getAllLoans() {
        // Asumiendo que libraryService tiene un método para obtener todos los préstamos
        // y que la SinglyLinkedList se convierte correctamente a List<Loan>
        List<Loan> allLoans = libraryService.getAllLoans();

        if (allLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }

        return new ResponseEntity<>(allLoans, HttpStatus.OK); // 200 OK
    }
    // DENTRO DE LoanController.java

    // Endpoint: POST /api/loans/{loanId}/return
    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnLoan(@PathVariable int loanId) {

        Loan returnedLoan = libraryService.returnLoan(loanId);

        if (returnedLoan == null) {
            // Asumimos que si retorna null es por 404 o 400 (ya devuelto/no existe)
            return new ResponseEntity<>("Error al devolver el préstamo. Verifique el ID o si ya está devuelto.", HttpStatus.BAD_REQUEST);
        }

        // Devolvemos el préstamo actualizado (con status 'RETURNED')
        String message;
        if ("RETURNED".equals(returnedLoan.getStatus())) {
            // El servicio ya gestionó la reserva, solo indicamos la devolución exitosa
            message = "Devolución exitosa para el Préstamo ID " + loanId + ".";
            System.out.println(message);
        } else {
            message = "Devolución registrada con advertencias.";
        }

        return new ResponseEntity<>(returnedLoan, HttpStatus.OK);
    }
// DENTRO DE LibraryController.java

    // Endpoint: POST /api/history/undo
    @PostMapping("/history/undo")
    public ResponseEntity<String> undoAction() {

        // El 'new' extra y la sintaxis anterior en las líneas 72-73 deben ser eliminados.
        // Asegúrate de que solo tengas esto:
        String result = libraryService.undoLastAction();

        if (result.startsWith("Error")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}