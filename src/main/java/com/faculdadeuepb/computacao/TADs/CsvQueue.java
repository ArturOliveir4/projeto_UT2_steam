package com.faculdadeuepb.computacao.TADs;

public class CsvQueue {

    private static class Node {
        String[] data;
        Node next;

        Node(String[] data){
            this.data = data;
            this.next = null;
        }
    }

    
    private Node front;  
    private Node rear;   
    private int size;

    public CsvQueue(){
        front = null;
        rear = null;
        size = 0;
    }

    public void enqueue(String[] data){
        Node newNode = new Node(data);

        if(rear == null){
            front = newNode;
            rear = newNode;
        }else{
            rear.next = newNode;
            rear = newNode;
        }

        size++;
    }

    public String[] dequeue(){
        if(front == null){
            return null; 
        }

        String[] data = front.data;
        front = front.next;

        if(front == null){
            rear = null;
        }

        size--;
        return data;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }
}
