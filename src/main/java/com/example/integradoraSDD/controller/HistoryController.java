package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.model.HistoryAction;
import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history") // Base URL para historial
public class HistoryController {

    @Autowired
    private LibraryService libraryService;

    // GET /api/history (Para ver todo el historial)
    @GetMapping
    public ResponseEntity<List<HistoryAction>> getHistory() {
        try {
            List<HistoryAction> history = libraryService.getHistory();

            if (history.isEmpty()) {
                // 204 No Content si la pila está vacía
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // 200 OK con la lista del historial
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo genérico de errores (ej. si falla la conversión a lista)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /api/history/undo (El método que usamos antes para deshacer)
    @PostMapping("/undo")
    public ResponseEntity<String> undoAction() {
        try {
            String result = libraryService.undoLastAction();

            if (result.startsWith("Error")) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Maneja el error si la pila está vacía
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}