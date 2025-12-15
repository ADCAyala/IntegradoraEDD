package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.model.Loan;
import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}