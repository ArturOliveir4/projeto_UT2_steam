package com.faculdadeuepb.computacao.TADs;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.faculdadeuepb.computacao.algorithms.MatrixTransformations;
import com.faculdadeuepb.computacao.model.utils.Date;



public class CsvLinkedList {
    private CsvNode head;

    public CsvLinkedList(){
        this.head = null;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void add(String[] data){
        CsvNode newNode = new CsvNode(data);
        if(head == null){
            head = newNode;
            return;
        }

        CsvNode current = head;
        while(current.next != null){
            current = current.next;
        }
        current.next = newNode;
    }

    public boolean remove(String[] data){
        if(head == null){
            return false;
        }

        if(java.util.Arrays.equals(head.data, data)){
            head = head.next;
            return true;
        }

        CsvNode current = head;
        while (current.next != null && !java.util.Arrays.equals(current.next.data, data)) {
            current = current.next;
        }

        if (current.next == null) return false;

        current.next = current.next.next;
        return true;
    }

    public void print(){
        CsvNode current = head;
        while(current != null){
            System.out.print(java.util.Arrays.toString(current.data) + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Getter para o head
    public CsvNode getHead() {
        return head;
    }

    public void insertionSortByReleaseDate(){
        if(head == null || head.next == null){
            return;
        } 

        CsvNode sorted = null;
        CsvNode current = head;

        while(current != null){
            CsvNode next = current.next;

            if(sorted == null || Date.checkDateSize(current.data[2], sorted.data[2])){
                current.next = sorted;
                sorted = current;
            }else{
                CsvNode temp = sorted;
                while(temp.next != null && !Date.checkDateSize(current.data[2], temp.next.data[2])){
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }

            current = next;
        }

        head = sorted;
    }


    public void insertionSortByPrice() {
        if(head == null || head.next == null){
            return;
        } 

        CsvNode sorted = null;
        CsvNode current = head;

        while(current != null){
            CsvNode next = current.next;
            double currentPrice = Double.parseDouble(current.data[6]);

            if(sorted == null || currentPrice < Double.parseDouble(sorted.data[6])){
                current.next = sorted;
                sorted = current;
            }else{
                CsvNode temp = sorted;
                while(temp.next != null && currentPrice >= Double.parseDouble(temp.next.data[6])){
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }

            current = next;
        }

        head = sorted;
    }

    public void insertionSortByAchievements() {
        if(head == null || head.next == null){
            return;
        } 

        CsvNode sorted = null;
        CsvNode current = head;
        while(current != null){
            CsvNode next = current.next;

            int currentAchievements = MatrixTransformations.safeParseInt(current.data[26]);

            if(sorted == null || currentAchievements > safeParseInt(sorted.data[26])){
                current.next = sorted;
                sorted = current;
            }else{
                CsvNode temp = sorted;
                while(temp.next != null && currentAchievements <= safeParseInt(temp.next.data[26])){
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }

            current = next;
        }

        head = sorted;
    }

    public static int safeParseInt(String s){
        try{
            return Integer.parseInt(s.trim());
        }catch(Exception e){
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
        for (String[] data : arrayList) {
            add(data);  
        }
    }   

    // --------------- BEST AND WORST CASES -----------------

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




