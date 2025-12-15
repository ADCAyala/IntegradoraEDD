
package com.example.integradoraSDD.service;

import com.example.integradoraSDD.model.HistoryAction;
import com.example.integradoraSDD.structures.ArrayStack;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HistoryService {
    // Usando tu ArrayStack con capacidad de 100
    private ArrayStack<HistoryAction> stack;

    public HistoryService() {
        this.stack = new ArrayStack<>(100);
    }

    public void push(HistoryAction action) {
        this.stack.push(action);
    }

    public HistoryAction pop() {
        return this.stack.pop();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public List<HistoryAction> getHistory() {
        return this.stack.toList();
    }
}