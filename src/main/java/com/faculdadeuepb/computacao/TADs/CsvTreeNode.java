package com.faculdadeuepb.computacao.TADs;

public class CsvTreeNode {
    String[] data;
    CsvTreeNode left, right;
    int height;

    public CsvTreeNode(String[] data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
