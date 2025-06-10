package com.faculdadeuepb.computacao.TADs;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.faculdadeuepb.computacao.algorithms.MatrixTransformations;
import com.faculdadeuepb.computacao.model.utils.Date;

public class CsvLinkedList {
    private CsvNode head;
    private CsvNode tail; 

    public CsvLinkedList(){
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

        while (current != null) {
            if (java.util.Arrays.equals(current.data, data)) {
                if (current.prev != null)
                    current.prev.next = current.next;
                else
                    head = current.next;

                if (current.next != null)
                    current.next.prev = current.prev;
                else
                    tail = current.prev;

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

    public CsvNode getHead() {
        return head;
    }

    public void insertionSortByReleaseDate(){
        if(head == null || head.next == null){
            return;
        } 

        CsvNode current = head.next;
        while(current != null){
            CsvNode key = current;
            CsvNode prev = current.prev;
            current = current.next;

            while(prev != null && Date.checkDateSize(key.data[2], prev.data[2])){
                prev = prev.prev;
            }

            if(key.prev != null){
                key.prev.next = key.next;
            } 

            if(key.next != null){
                key.next.prev = key.prev;   
            } 

            if(prev == null){
                key.next = head;
                head.prev = key;
                key.prev = null;
                head = key;
            }else{
                key.next = prev.next;
                if (prev.next != null) prev.next.prev = key;
                prev.next = key;
                key.prev = prev;
            }
        }

        CsvNode temp = head;
        while(temp.next != null) temp = temp.next;
        tail = temp;
    }

    public void insertionSortByPrice() {
        if(head == null || head.next == null) return;

        CsvNode current = head.next;
        while(current != null){
            CsvNode key = current;
            CsvNode prev = current.prev;
            current = current.next;

            double keyPrice = Double.parseDouble(key.data[6]);

            while(prev != null && keyPrice < Double.parseDouble(prev.data[6])){
                prev = prev.prev;
            }

            if (key.prev != null) key.prev.next = key.next;
            if (key.next != null) key.next.prev = key.prev;

            if (prev == null) {
                key.next = head;
                head.prev = key;
                key.prev = null;
                head = key;
            } else {
                key.next = prev.next;
                if (prev.next != null) prev.next.prev = key;
                prev.next = key;
                key.prev = prev;
            }
        }

        CsvNode temp = head;
        while(temp.next != null) temp = temp.next;
        tail = temp;
    }

    public void insertionSortByAchievements() {
        if(head == null || head.next == null) return;

        CsvNode current = head.next;
        while(current != null){
            CsvNode key = current;
            CsvNode prev = current.prev;
            current = current.next;

            int keyAch = MatrixTransformations.safeParseInt(key.data[26]);

            while(prev != null && keyAch > safeParseInt(prev.data[26])){
                prev = prev.prev;
            }

            if (key.prev != null) key.prev.next = key.next;
            if (key.next != null) key.next.prev = key.prev;

            if (prev == null) {
                key.next = head;
                head.prev = key;
                key.prev = null;
                head = key;
            } else {
                key.next = prev.next;
                if (prev.next != null) prev.next.prev = key;
                prev.next = key;
                key.prev = prev;
            }
        }

        CsvNode temp = head;
        while(temp.next != null) temp = temp.next;
        tail = temp;
    }

    public static int safeParseInt(String s){
        try {
            return Integer.parseInt(s.trim());
        } catch(Exception e){
            return 0; 
        }
    }

    public List<String[]> toArrayList() {
        List<String[]> list = new ArrayList<>();
        CsvNode current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public void fromArrayList(List<String[]> arrayList) {
        head = null;
        tail = null;
        for (String[] data : arrayList) {
            add(data);  
        }
    }   

    // Métodos de ordenação com casos de teste
    public void insertionSortByReleaseDate_bestCase() {
        List<String[]> arrayList = this.toArrayList();
        arrayList.sort(dateComparator);  
        this.fromArrayList(arrayList);
        this.insertionSortByReleaseDate();
    }

    public void insertionSortByReleaseDate_worstCase() {
        List<String[]> arrayList = this.toArrayList();
        arrayList.sort(dateComparator.reversed());
        this.fromArrayList(arrayList);
        this.insertionSortByReleaseDate();
    }

    public void insertionSortByPrice_bestCase() {
        List<String[]> arrayList = this.toArrayList();
        arrayList.sort(priceComparator);  
        this.fromArrayList(arrayList);
        this.insertionSortByPrice();
    }

    public void insertionSortByPrice_worstCase() {
        List<String[]> arrayList = this.toArrayList();
        arrayList.sort(priceComparator.reversed());
        this.fromArrayList(arrayList);
        this.insertionSortByPrice();
    }

    public void insertionSortByAchievements_bestCase() {
        List<String[]> arrayList = this.toArrayList();
        arrayList.sort(achievementsComparator);  
        this.fromArrayList(arrayList);
        this.insertionSortByAchievements();
    }

    public void insertionSortByAchievements_worstCase() {
        List<String[]> arrayList = this.toArrayList();
        arrayList.sort(achievementsComparator.reversed());
        this.fromArrayList(arrayList);
        this.insertionSortByAchievements();
    }

    Comparator<String[]> dateComparator = (a, b) -> {
        if (Date.checkDateSize(a[2], b[2])) return -1;
        else if (Date.checkDateSize(b[2], a[2])) return 1;
        else return 0;
    };

    Comparator<String[]> priceComparator = (a, b) -> {
        double priceA = Double.parseDouble(a[6]);
        double priceB = Double.parseDouble(b[6]);
        return Double.compare(priceA, priceB); 
    };

    Comparator<String[]> achievementsComparator = (a, b) -> {
        int achA = safeParseInt(a[26]);
        int achB = safeParseInt(b[26]);
        return Integer.compare(achA, achB); 
    };
}
