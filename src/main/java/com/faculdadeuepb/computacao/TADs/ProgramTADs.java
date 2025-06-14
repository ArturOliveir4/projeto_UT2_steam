package com.faculdadeuepb.computacao.TADs;
import com.faculdadeuepb.computacao.model.utils.Transformations;

public class ProgramTADs {
    public static void main(String[] args) throws Exception {
        // Criaçã dos arquivos iniciais (sem ordenação)
        Transformations.createFiles();

        // Conversão do csv para AVL | Usamos AVL para garantir inserções e buscas balanceadas, evitando pior caso O(n)
        CsvAVLTree tree = TADsTransformations.csvToAVLTree("games_formated_release_data.csv", TADsTransformations.dateComparator);
        
        // Criação dos arquivos ordenados por data
        TADsTransformations.createCsv_MergeSortReleaseDate_MediumCase(tree);
        TADsTransformations.createCsv_MergeSortReleaseDate_BestCase(tree);
        TADsTransformations.createCsv_MergeSortReleaseDate_WorstCase(tree);

        // Conversão do csv para Lista Duplamente Encadeada | Usamos lista duplamente encadeada para facilitar a inserção e remoção sequencial de elementos, mantendo a ordem original dos dados do CSV sem balanceamento automático 
        CsvDoubleLinkedList list = TADsTransformations.csvToDoublyLinkedList("games_formated_release_data.csv");

        // Criação dos arquivos ordenados por preço
        TADsTransformations.createCsv_MergeSortPrice_MediumCase(list);
        TADsTransformations.createCsv_MergeSortPrice_BestCase(list);
        TADsTransformations.createCsv_MergeSortPrice_WorstCase(list);

        // Criação dos arquivos ordenados por conquistas
        TADsTransformations.createCsv_MergeSortAchievements_MediumCase(list);
        TADsTransformations.createCsv_MergeSortAchievements_BestCase(list);
        TADsTransformations.createCsv_MergeSortAchievements_WorstCase(list);

    }
}
