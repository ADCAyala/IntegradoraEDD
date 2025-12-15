// DENTRO DE UserController.java

package com.example.integradoraSDD.controller;

import com.example.integradoraSDD.model.User;
import com.example.integradoraSDD.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private LibraryService libraryService;

    // 2.2. Usuarios - POST /api/users
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        // Asumiendo que has agregado el método crearUsuario en LibraryService
        User createdUser = libraryService.crearUsuario(newUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // 2.2. Usuarios - GET /api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = libraryService.getAllUsers(); // Asumiendo que existe el método getAllUsers
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 2.2. Usuarios - GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = libraryService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}