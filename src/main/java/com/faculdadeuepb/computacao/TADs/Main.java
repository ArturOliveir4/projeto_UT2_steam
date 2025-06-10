package com.faculdadeuepb.computacao.TADs;
import com.faculdadeuepb.computacao.model.utils.Transformations;

public class Main {
    public static void main(String[] args) throws Exception {
        Transformations.createFiles();

        try {
           CsvLinkedList list = TADsTransformations.csvToLinkedList("games_formated_release_data.csv");
           TADsTransformations.createCsv_linkedList_InsertionSortReleaseDate_MediumCase(list);
           TADsTransformations.createCsv_linkedList_InsertionSortReleaseDate_BestCase(list);
           TADsTransformations.createCsv_linkedList_InsertionSortReleaseDate_WorstCase(list);
           
        } catch (Exception e){

        }

    }
}
