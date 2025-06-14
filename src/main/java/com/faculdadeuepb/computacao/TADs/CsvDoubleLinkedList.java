package com.faculdadeuepb.computacao.TADs;
import java.util.ArrayList;
import java.util.List;

public class CsvDoubleLinkedList {
    private CsvNode head;
    private CsvNode tail; 

    public CsvDoubleLinkedList(){
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void add(String[] data){
        CsvNode newNode = new CsvNode(data);
        if (head == null){
            head = newNode;
            tail = newNode;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public boolean remove(String[] data){
        CsvNode current = head;

        while(current != null){
            if(java.util.Arrays.equals(current.data, data)){
                if(current.prev != null){
                    current.prev.next = current.next;
                }else{
                    head = current.next;
                }

                if(current.next != null){
                    current.next.prev = current.prev;
                }else{
                    tail = current.prev;
                }
                    
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void print(){
        CsvNode current = head;
        while(current != null){
            System.out.print(java.util.Arrays.toString(current.data) + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public CsvNode getHead(){
        return head;
    }

    public List<String[]> toArrayList(){
        List<String[]> list = new ArrayList<>();
        CsvNode current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public void fromArrayList(List<String[]> arrayList){
        head = null;
        tail = null;
        for(String[] data : arrayList){
            add(data);  
        }
    }   

}
