package com.example.integradoraSDD.structures;

import com.example.integradoraSDD.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SinglyLinkedList <T> {
    Node head;

    public void add(T data){ //agregaremos cualquier cosa T
        Node nuevo = new Node(data);

        if(head == null){
            head = nuevo;
            return;
        }
        Node temp = head;  //mueve hasta el final de la lista para que se inserte al ultimo
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = nuevo;
    }

    public void removeAll( T data){ // removeremos un objeto T
        if(head == null){
            return;
        }
        if(head.data == data){ //Si la cabeza es el objeto buscado el enlace se pasa al siguiente y se borra por el recolector de basura
            head = head.next;
            return;
        }

        Node temp = head;
        while(temp.next != null &&  temp.next.data != data){//en caso de que se quiera borrar el ultimo
            temp = temp.next;
        }

        if(temp.next != null){//el caso de que se quiera borrar uno de en medio
            temp.next = temp.next.next;
        }

    }

    public boolean contains(T data){ //el contains va a saber si un dato existe
        Node temp = head;
        while(temp != null){
            if(temp.data == data){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void update(T data, T newData){
        Node temp = head;
        while(temp != null){
            if(temp.data == data){
                temp.data = newData;
            }
            temp = temp.next;
        }
    }

    public void removeLogic(T data){
        Node temp = head;
        while(temp != null){
            if(temp.data == data){
                ((Book) temp.data).setActive(false); //borrado de manera lógica
            }
            temp = temp.next;
        }
    }

    public void printList(){
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");

    }

    public Node getHead(){
        return head;
    }

    public T get(int index) {
        if (index < 0 || head == null) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Node<T> temp = head;
        int count = 0;
        while (temp != null) {
            if (count == index) {
                return temp.data;
            }
            temp = temp.next;
            count++;
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango");
    }

    public int size() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node<T> temp = head; // Usa el tipo genérico Node<T> para seguridad
        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }
        return list;
    }

}
