package com.faculdadeuepb.computacao.TADs;
import com.faculdadeuepb.computacao.model.utils.Transformations;

public class Main {
    public static void main(String[] args) throws Exception {
        Transformations.createFiles();

        try {
           // Usamos AVL para garantir inserções e buscas balanceadas, evitando pior caso O(n)
           CsvAVLTree tree = TADsTransformations.csvToAVLTree("games_formated_release_data.csv", TADsTransformations.dateComparator);
           
           TADsTransformations.createCsv_MergeSortReleaseDate_MediumCase(tree);
           TADsTransformations.createCsv_MergeSortReleaseDate_BestCase(tree);
           TADsTransformations.createCsv_MergeSortReleaseDate_WorstCase(tree);

           // Usamos lista duplamente encadeada para facilitar a inserção e remoção sequencial de elementos, mantendo a ordem original dos dados do CSV sem balanceamento automático 
           CsvLinkedList list = TADsTransformations.csvToDoublyLinkedList("games_formated_release_data.csv");

           TADsTransformations.createCsv_MergeSortPrice_MediumCase(list);
           TADsTransformations.createCsv_MergeSortPrice_BestCase(list);
           TADsTransformations.createCsv_MergeSortPrice_WorstCase(list);



        } catch (Exception e){

        }

    }
}
