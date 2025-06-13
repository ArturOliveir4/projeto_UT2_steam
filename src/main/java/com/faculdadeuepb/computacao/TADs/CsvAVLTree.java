package com.faculdadeuepb.computacao.TADs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CsvAVLTree {
    private CsvTreeNode root;
    private Comparator<String[]> comparator;

    public CsvAVLTree(Comparator<String[]> comparator){
        this.root = null;
        this.comparator = comparator;
    }

    public void insert(String[] data){
        root = insertRecursive(root, data);
    }

    public CsvTreeNode getRoot(){
        return this.root;
    }

    private CsvTreeNode insertRecursive(CsvTreeNode node, String[] data){
        if(node == null){
            return new CsvTreeNode(data);
        }

        if(comparator.compare(data, node.data) < 0){
            node.left = insertRecursive(node.left, data);
        }else{
            node.right = insertRecursive(node.right, data);
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        if(balance > 1 && comparator.compare(data, node.left.data) < 0){
            return rotateRight(node);
        }

        if(balance < -1 && comparator.compare(data, node.right.data) > 0){
            return rotateLeft(node);
        }

        if(balance > 1 && comparator.compare(data, node.left.data) > 0){
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if(balance < -1 && comparator.compare(data, node.right.data) < 0){
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private int getHeight(CsvTreeNode node){
        if(node == null){
            return 0;
        }else{
            return node.height;
        }
    }

    private int getBalance(CsvTreeNode node){
        if(node == null){
            return 0;
        }else{
            return getHeight(node.left) - getHeight(node.right);
        }
    }

    private CsvTreeNode rotateRight(CsvTreeNode y){
        CsvTreeNode x = y.left;
        CsvTreeNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private CsvTreeNode rotateLeft(CsvTreeNode x){
        CsvTreeNode y = x.right;
        CsvTreeNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    public List<String[]> inOrderTraversal(){
        List<String[]> result = new ArrayList<>();
        inOrderTraversalRecursive(root, result);
        return result;
    }

    private void inOrderTraversalRecursive(CsvTreeNode node, List<String[]> list){
        if(node != null){
            inOrderTraversalRecursive(node.left, list);
            list.add(node.data);
            inOrderTraversalRecursive(node.right, list);
        }
    }
}

