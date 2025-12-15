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

    @Autowired
    private LibraryService libraryService;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Map<String, Integer> payload) {

        Integer userId = payload.get("userId");
        Integer bookId = payload.get("bookId");

        if (userId == null || bookId == null) {
            return new ResponseEntity<>("Debe proporcionar userId y bookId.", HttpStatus.BAD_REQUEST);
        }

        Loan result = libraryService.createLoanOrReservation(userId, bookId);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>("Proceso finalizado. Revise la lista de espera o si ya tenía un préstamo activo.", HttpStatus.OK);
        }
    }


    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> allLoans = libraryService.getAllLoans();

        if (allLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }

        return new ResponseEntity<>(allLoans, HttpStatus.OK); // 200 OK
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnLoan(@PathVariable int loanId) {

        Loan returnedLoan = libraryService.returnLoan(loanId);

        if (returnedLoan == null) {
            return new ResponseEntity<>("Error al devolver el préstamo. Verifique el ID o si ya está devuelto.", HttpStatus.BAD_REQUEST);
        }

        String message;
        if ("RETURNED".equals(returnedLoan.getStatus())) {
            message = "Devolución exitosa para el Préstamo ID " + loanId + ".";
            System.out.println(message);
        } else {
            message = "Devolución registrada con advertencias.";
        }

        return new ResponseEntity<>(returnedLoan, HttpStatus.OK);
    }

    @PostMapping("/history/undo")
    public ResponseEntity<String> undoAction() {

        String result = libraryService.undoLastAction();

        if (result.startsWith("Error")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> activeLoans = libraryService.getActiveLoans();

        if (activeLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activeLoans, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable int userId) {
        List<Loan> userLoans = libraryService.getLoansByUserId(userId);

        if (userLoans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }
        return new ResponseEntity<>(userLoans, HttpStatus.OK);
    }

}