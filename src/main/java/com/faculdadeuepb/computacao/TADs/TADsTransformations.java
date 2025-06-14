package com.faculdadeuepb.computacao.TADs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.faculdadeuepb.computacao.algorithms.MatrixTransformations;
import com.faculdadeuepb.computacao.algorithms.MergeSort;
import com.faculdadeuepb.computacao.model.utils.CSVCreate;

public class TADsTransformations {
    // Atributo header utilizado na criação de todos os .csv dessa classe
    private static String[] header;

    // Bloco estático para inicializar o cabeçalho
    static {
        try {
            header = getCsvHeader("games_formated_release_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para obter o cabeçalho do arquivo .csv
    public static String[] getCsvHeader(String csvFileName) throws IOException {
        File csvFile = new File(csvFileName);
        try(
            FileReader reader = new FileReader(csvFile);
            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)
        ){
            return parser.getHeaderMap().keySet().toArray(new String[0]);
        }
    }

    // Função que converte o arquivo .csv passado por parâmetro em String em lista encadeada
    public static CsvDoubleLinkedList csvToLinkedList(String csvFileName) throws IOException {
        File csvFile = new File(csvFileName);
        CsvDoubleLinkedList list = new CsvDoubleLinkedList();

        CSVParser parser = CSVCreate.initializeCSVParser(csvFile);
        for(CSVRecord record : parser){
            int columns = record.size();
            String[] row = new String[columns];
            for(int i = 0; i < columns; i++){
                row[i] = record.get(i);
            }
            list.add(row);
        }
        parser.close();

        return list;
    }

    // Função para testes
    public static CsvDoubleLinkedList csvToLinkedList1000(String csvFileName) throws IOException {
        File csvFile = new File(csvFileName);
        CsvDoubleLinkedList list = new CsvDoubleLinkedList();

        CSVParser parser = CSVCreate.initializeCSVParser(csvFile);
        int maxLines = 100;
        int count = 0;

        for(CSVRecord record : parser){
            if(count >= maxLines){
                break;
            } 

            int columns = record.size();
            String[] row = new String[columns];
            for(int i = 0; i < columns; i++){
                row[i] = record.get(i);
            }
            list.add(row);
            count++;

        }
        parser.close();

        return list;
    }

    // Função que gera um arquivo .csv a partir de uma fila (CsvQueue) contendo os dados ordenados. 
    public static void createCsv(CsvQueue queue, String nomeArquivo) throws IOException {
        File outputCsv = new File(nomeArquivo);

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(outputCsv), 
                CSVFormat.DEFAULT.withHeader(header))){

            while (!queue.isEmpty()) {
                String[] data = queue.dequeue();
                csvPrinter.printRecord((Object[]) data);
            }

            csvPrinter.flush();
        }
    }


    //Função que converter Linked List para arvoreAVL
    public static CsvAVLTree convertLinkedListToAVL(CsvDoubleLinkedList list, Comparator<String[]> comparator) {
        CsvAVLTree tree = new CsvAVLTree(comparator);
        CsvNode current = list.getHead();
        while(current != null){
            tree.insert(current.data);
            current = current.next;
        }
        return tree;
    }


    //Função que converter .csv para arvore AVL
    public static CsvAVLTree csvToAVLTree(String csvFileName, Comparator<String[]> comparator) throws IOException {
        File csvFile = new File(csvFileName);
        CsvAVLTree tree = new CsvAVLTree(comparator);
        CSVParser parser = CSVCreate.initializeCSVParser(csvFile);

        for(CSVRecord record : parser){
            String[] row = new String[record.size()];
            for(int i = 0; i < record.size(); i++){
                row[i] = record.get(i);
            }
            tree.insert(row);
        }

        parser.close();
        return tree;
    }


    //Função que converte arvore AVL para Matriz
    public static String[][] avlTreeToMatrix(CsvAVLTree tree){
        List<String[]> dataList = new ArrayList<>();
        preOrderTraversal(tree.getRoot(), dataList);

        if(dataList.isEmpty()){
            return new String[0][0];
        }

        int rows = dataList.size();
        int columns = dataList.get(0).length;
        String[][] matrix = new String[rows][columns];

        for(int i = 0; i < rows; i++){
            matrix[i] = dataList.get(i);
        }

        return matrix;
    }

    private static void preOrderTraversal(CsvTreeNode node, List<String[]> dataList){
        if(node == null){
            return;
        } 

        dataList.add(node.data);  
        preOrderTraversal(node.left, dataList);
        preOrderTraversal(node.right, dataList);
    }

    //Função que converte matriz em fila
    public static CsvQueue matrixToQueue(String[][] matriz){
        CsvQueue fila = new CsvQueue();
        for(int i = 0; i < matriz.length; i++){
            fila.enqueue(matriz[i]);
        }
        return fila;
    }

    public static CsvDoubleLinkedList csvToDoublyLinkedList(String csvFileName) throws IOException{
        File csvFile = new File(csvFileName);
        CsvDoubleLinkedList list = new CsvDoubleLinkedList();

        CSVParser parser = CSVCreate.initializeCSVParser(csvFile);

        for(CSVRecord record : parser){
            String[] data = new String[record.size()];
            for(int i = 0; i < record.size(); i++){
                data[i] = record.get(i);
            }
            list.add(data);
        }
        parser.close();

        return list;
    }

    //Função que transforma lista Duplamente encadeada em matriz
    public static String[][] doublyLinkedListToMatrix(CsvDoubleLinkedList list) {
        int rowCount = 0;
        int columnCount = 0;

        CsvNode current = list.getHead();
        if(current != null){
            columnCount = current.data.length;
        }

        while(current != null){
            rowCount++;
            current = current.next;
        }

        String[][] matrix = new String[rowCount][columnCount];

        current = list.getHead();
        int i = 0;
        while(current != null){
            System.arraycopy(current.data, 0, matrix[i], 0, columnCount);
            i++;
            current = current.next;
        }

        return matrix;
    }


    public static void createCsv_MergeSortReleaseDate_MediumCase(CsvAVLTree tree) throws IOException{  
        System.out.println("\nGenerating 'games_release_date_mergeSort_medioCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.avlTreeToMatrix(tree);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_RealeseDate(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_release_date_mergeSort_medioCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortReleaseDate_BestCase(CsvAVLTree tree) throws IOException{  
        System.out.println("\nGenerating 'games_release_date_mergeSort_melhorCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.avlTreeToMatrix(tree);

        // Ordenando previamente a matriz (MELHOR CASO)
        MatrixTransformations.orderJava_Data_Crescente(formatedCsvMatrix);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_RealeseDate(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_release_date_mergeSort_melhorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortReleaseDate_WorstCase(CsvAVLTree tree) throws IOException{  
        System.out.println("\nGenerating 'games_release_date_mergeSort_piorCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.avlTreeToMatrix(tree);

        // Ordenando previamente a matriz (PIOR CASO)
        MatrixTransformations.orderJava_Data_Descrescente(formatedCsvMatrix);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_RealeseDate(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_release_date_mergeSort_piorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }




    public static void createCsv_MergeSortPrice_MediumCase(CsvDoubleLinkedList list) throws IOException{  
        System.out.println("\nGenerating 'games_price_mergeSort_medioCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.doublyLinkedListToMatrix(list);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_Price(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_price_mergeSort_medioCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortPrice_BestCase(CsvDoubleLinkedList list) throws IOException{  
        System.out.println("\nGenerating 'games_price_mergeSort_melhorCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.doublyLinkedListToMatrix(list);

        // Ordenando previamente a matriz (MELHOR CASO)
        MatrixTransformations.orderJava_Data_Crescente(formatedCsvMatrix);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_Price(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_price_mergeSort_melhorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortPrice_WorstCase(CsvDoubleLinkedList list) throws IOException{  
        System.out.println("\nGenerating 'games_price_mergeSort_piorCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.doublyLinkedListToMatrix(list);

        // Ordenando previamente a matriz (PIOR CASO)
        MatrixTransformations.orderJava_Data_Descrescente(formatedCsvMatrix);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_Price(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_price_mergeSort_piorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortAchievements_MediumCase(CsvDoubleLinkedList list) throws IOException{  
        System.out.println("\nGenerating 'games_achievements_mergeSort_medioCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.doublyLinkedListToMatrix(list);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_Achievements(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_achievements_mergeSort_medioCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortAchievements_BestCase(CsvDoubleLinkedList list) throws IOException{  
        System.out.println("\nGenerating 'games_achievements_mergeSort_melhorCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.doublyLinkedListToMatrix(list);

        // Ordenando previamente a matriz (MELHOR CASO)
        MatrixTransformations.orderJava_Data_Crescente(formatedCsvMatrix);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_Achievements(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_achievements_mergeSort_melhorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static void createCsv_MergeSortAchievements_WorstCase(CsvDoubleLinkedList list) throws IOException{  
        System.out.println("\nGenerating 'games_achievements_mergeSort_piorCaso.csv'");
        
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Matriz para usar os algoritmos de ordenação, que é eficiente e direto para ordenação
        String formatedCsvMatrix[][] = TADsTransformations.doublyLinkedListToMatrix(list);

        // Ordenando previamente a matriz (PIOR CASO)
        MatrixTransformations.orderJava_Data_Descrescente(formatedCsvMatrix);

        // Ordenação com insertion sort dentro da matriz
        formatedCsvMatrix = MergeSort.mergeSort_Achievements(formatedCsvMatrix, formatedCsvMatrix.length); 

        // Fila para manter a ordem dos elementos ordenados de forma simples e sequencial, facilitando a geração do CSV
        CsvQueue queue = TADsTransformations.matrixToQueue(formatedCsvMatrix);


        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        // Criando de fato o arquivo .csv
        createCsv(queue, "games_achievements_mergeSort_piorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");
    }

    public static int safeParseInt(String s){
        try{
            return Integer.parseInt(s.trim());
        } catch(Exception e){
            return 0; 
        }
    }

    public static Comparator<String[]> dateComparator = (a, b) -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dateStrA = a[2];
        String dateStrB = b[2];

        if(dateStrA == null || dateStrA.isBlank()){
            return -1;
        } 
        if(dateStrB == null || dateStrB.isBlank()){
            return 1;
        } 

        try{
            LocalDate dateA = LocalDate.parse(dateStrA, formatter);
            LocalDate dateB = LocalDate.parse(dateStrB, formatter);
            int result = dateA.compareTo(dateB);
            if (result != 0) return result;

            return a[0].compareToIgnoreCase(b[0]);

        } catch(Exception e){
            return dateStrA.compareTo(dateStrB);
        }
    };

    public static Comparator<String[]> priceComparator = (a, b) -> {
        double priceA = Double.parseDouble(a[6]);
        double priceB = Double.parseDouble(b[6]);
        return Double.compare(priceA, priceB); 
    };

    public static Comparator<String[]> achievementsComparator = (a, b) -> {
        int achA = safeParseInt(a[26]);
        int achB = safeParseInt(b[26]);
        return Integer.compare(achA, achB); 
    };
    

    
}