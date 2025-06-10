package com.faculdadeuepb.computacao.TADs;

public class CsvNode {
    String[] data;
    CsvNode next;
    CsvNode prev; 

    public CsvNode(String[] data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
