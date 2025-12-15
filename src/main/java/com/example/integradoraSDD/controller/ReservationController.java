package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private LibraryService libraryService;


    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Integer>> getWaitlistByBook(@PathVariable int bookId) {

        List<Integer> waitlist = libraryService.getWaitlistForBook(bookId);

        if (waitlist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Libro no encontrado
        }

        if (waitlist.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Cola vacía
        }

        return new ResponseEntity<>(waitlist, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<String> cancelReservation(
            @RequestParam("userId") int userId,
            @RequestParam("bookId") int bookId) {

        String result = libraryService.cancelReservation(userId, bookId);

        if (result.startsWith("Error")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}