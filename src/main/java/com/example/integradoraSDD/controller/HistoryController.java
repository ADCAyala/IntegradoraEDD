package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.model.HistoryAction;
import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<HistoryAction>> getHistory() {
        try {
            List<HistoryAction> history = libraryService.getHistory();

            if (history.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/undo")
    public ResponseEntity<String> undoAction() {
        try {
            String result = libraryService.undoLastAction();

            if (result.startsWith("Error")) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}