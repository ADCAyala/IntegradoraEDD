package com.example.integradoraSDD.model;

import com.example.integradoraSDD.structures.ArrayQueue;

import java.util.LinkedList;
import java.util.Queue; // Usaremos la interfaz Queue de Java para simular ArrayQueue


public class Book {
    private int id;
    private String title;
    private String author;
    private String category;

    private int totalCopies;
    private int availableCopies;
    private boolean active;

    // Asumiendo que ArrayQueue<Integer> es similar a java.util.Queue<Integer>
    private ArrayQueue<Integer> waitlist;

    // Constructor
    public Book(int id, String title, String author, String category, int totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Inicialmente, todas las copias están disponibles
        this.active = true; // Inicialmente, el libro está activo
        this.waitlist = new ArrayQueue<>(50); // Inicializamos la cola de espera
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean isActive() {
        return active;
    }

    public ArrayQueue<Integer> getWaitlist() {
        return waitlist;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    //------------------------------ Métodos copias------------------------


    public void increaseAvailableCopies() {
        if (availableCopies < totalCopies) {
            this.availableCopies++;
        }
    }

    public void decreaseAvailableCopies() {
        if (availableCopies > 0) {
            this.availableCopies--;
        }
    }

    public void addToWaitlist(int userId) {
        this.waitlist.enqueue(userId);
    } // offer en Queue es como push/enqueue

    public Integer getNextInWaitlist() {
        return this.waitlist.peek();
    } // peek para ver el primero

    public Integer removeNextInWaitlist() {
        return this.waitlist.dequeue();
    } // poll es como pop/dequeue



}