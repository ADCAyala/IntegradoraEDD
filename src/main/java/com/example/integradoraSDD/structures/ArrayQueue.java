package com.example.integradoraSDD.structures;

public class ArrayQueue<T> {
    private T[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }


    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        this.capacity = 100; // Capacidad por defecto
        this.data = (T[]) new Object[this.capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean enqueue(T value) {
        if (isFull()){
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % capacity; // Movimiento circular
        size++;
        return true;
    }

    // DEQUEUE (Sacar del frente - front)
    public T dequeue() {
        if(isEmpty()){
            return null;
        }
        T value = data[front];
        data[front] = null; // Limpiar la referencia
        front = (front + 1) % capacity; // Movimiento circular
        size--;
        return value;
    }

    // PEEK (Ver el elemento del frente)
    public T peek() {
        if(isEmpty()){
            return null;
        }
        return data[front];
    }

    // Size
    public int size(){
        return size;
    }

    // isEmpty
    public boolean isEmpty(){
        return size == 0;
    }

    // isFull
    public boolean isFull(){
        return size == capacity;
    }
}