package com.example.integradoraSDD.structures;

public class ArrayStack<T> {
    private T[] data;
    private int top;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.top = -1;
    }

    //Push (agregar)
    public boolean push(T value) {
        if (isFull()){
            return false;
        }
        top++;
        data[top] = value;
        return true;
    }

    //POP (saca)

    public T pop() {
        if(isEmpty()){
            return null;
        }
        T value = data[top];
        data[top] = null;
        top--;
        return value;
    }
    //PEEK (ver el tope)
    public T peek() {
        if(isEmpty()){
            return null;
        }
        return data[top];
    }

    //size
    public int size(){return top+1;}

    //isEmpty
    public boolean isEmpty(){return top==-1;}

    //isFull
    public boolean isFull(){return top==capacity-1;}

    //Print Stack
    public void printStack(){
        if(isEmpty()){
            System.out.println("Pila vacía");
            return;
        }

        for (int i = top; i >= 0; i--){
            System.out.print(data[i]+" ");
            if (i>0){
                System.out.println(", ");
            }
        }
        System.out.println();
    }

}
