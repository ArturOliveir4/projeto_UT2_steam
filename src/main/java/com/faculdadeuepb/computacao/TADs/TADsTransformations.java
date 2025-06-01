package com.faculdadeuepb.computacao.TADs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

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
    public static CsvLinkedList csvToLinkedList(String csvFileName) throws IOException {
        File csvFile = new File(csvFileName);
        CsvLinkedList list = new CsvLinkedList();

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
    public static CsvLinkedList csvToLinkedList1000(String csvFileName) throws IOException {
        File csvFile = new File(csvFileName);
        CsvLinkedList list = new CsvLinkedList();

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

    public static void createCsv(CsvLinkedList list, String nomeArquivo) throws IOException {
        File outputCsv = new File(nomeArquivo);

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(outputCsv), 
                CSVFormat.DEFAULT.withHeader(header))) {

            CsvNode current = list.getHead();

            while (current != null) {
                csvPrinter.printRecord((Object[]) current.data); 
                current = current.next;
            }

            csvPrinter.flush();
        }
    }

    public static void createCsv_linkedList_InsertionSortReleaseDate_MediumCase(CsvLinkedList list) throws IOException{
        System.out.println("\nGenerating 'linkedList_games_release_date_insertionSort_medioCaso.csv'");

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        // Ordenando a lista passada por parâmetro    
        list.insertionSortByReleaseDate();
        

        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        createCsv(list, "games_release_date_insertionSort_medioCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");

    }

    public static void createCsv_linkedList_InsertionSortReleaseDate_BestCase(CsvLinkedList list) throws IOException{
        System.out.println("\nGenerating 'linkedList_games_release_date_insertionSort_melhorCaso.csv'");

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        list.insertionSortByReleaseDate_bestCase();
        

        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        createCsv(list, "games_release_date_insertionSort_melhorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");

    }

    public static void createCsv_linkedList_InsertionSortReleaseDate_WorstCase(CsvLinkedList list) throws IOException{
        System.out.println("\nGenerating 'linkedList_games_release_date_insertionSort_piorCaso.csv'");

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); 
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();

        list.insertionSortByReleaseDate_worstCase();
        

        long end = System.nanoTime();
        long duration = end - start; 

        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = memoriaDepois - memoriaAntes;

        createCsv(list, "games_release_date_insertionSort_piorCaso.csv");

        System.out.println("Done\nAverage execution time : " + duration + " ns\nMemory used on average: " + memoriaUsada + " bytes");

    }

}