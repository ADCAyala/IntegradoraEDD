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
    // DENTRO DE ArrayQueue<T> { ...

    /**
     * Remueve un elemento específico por su valor (necesario para el UNDO).
     * NOTA: Esto es una operación costosa O(n) en una cola implementada con array.
     */
    public boolean removeById(T value) {
        if (isEmpty()) {
            return false;
        }

        T[] temp = (T[]) new Object[capacity];
        int newRear = 0;
        int itemsCopied = 0;
        boolean found = false;

        // Itera desde el frente de la cola
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            T currentValue = data[index];

            // Si encontramos el valor y aún no lo hemos removido
            if (currentValue != null && currentValue.equals(value) && !found) {
                found = true; // Marca como encontrado, no lo copiamos al array temporal
            } else {
                temp[newRear++] = currentValue;
                itemsCopied++;
            }
        }

        if (found) {
            // Si se removió, actualiza la estructura interna
            data = temp;
            size = itemsCopied;
            front = 0;
            rear = itemsCopied;
            return true;
        }

        return false; // El elemento no fue encontrado
    }
}