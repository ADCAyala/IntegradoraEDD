// DENTRO DE BookController.java

package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.model.Book;
import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private LibraryService libraryService;

    // 2.1. Libros - POST /api/books
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book newBook) {
        Book createdBook = libraryService.crearLibro(newBook);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // 2.1. Libros - GET /api/books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryService.getAllLibros();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // 2.1. Libros - GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = libraryService.getByID(id);
        if (book == null || !book.isActive()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // 2.1. Libros - PATCH /api/books/{id}/status (Baja Lógica)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> deleteBookLogic(@PathVariable int id) {
        try {
            libraryService.deleteLibro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // NOTA: Los endpoints PUT (Update) y GET (Search) son opcionales y los omitimos por ahora para enfocarnos en la funcionalidad principal.
}