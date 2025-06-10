package com.faculdadeuepb.computacao.TADs;

import java.io.IOException;

public class Fila<T> {
    private T[] elementos;
    private int cabeca;
    private int cauda;
    private int tamanho;
    private int capacidade;

    public Fila(int capacidade) throws IOException {
        this.capacidade = capacidade;
        this.elementos = (T[]) new Object[capacidade];
        this.cabeca = 0;
        this.cauda = -1;
        this.tamanho = 0;
    }

    // Adiciona elemento no final da fila 
    public void enqueue(T elem) throws Exception {
        if (isFull()) {
            throw new Exception("Fila cheia!!");
        }
        cauda = (cauda + 1) % capacidade;
        elementos[cauda] = elem;
        tamanho++;
    }

    public T dequeue() throws Exception {
        if(isEmpty()){
            throw new Exception("Fila vazia!!");
        }
        T elemento = elementos[cabeca];
        cabeca = (cabeca + 1) % capacidade;
        tamanho--;
        return elemento;
    }

    // Retorna o elemento da cabeça sem remover
    public T head() {
        if (isEmpty()) {
            throw new RuntimeException("Fila vazia!!");
        }
        return elementos[cabeca];
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == capacidade;
    }

    public int size() {
        return tamanho;
    }
}