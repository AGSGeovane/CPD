package Utils;

import Entities.SexRatioEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mordor on 20/08/2016.
 */
public class SortUtils {

    public Map<String, String> sortByAllAlgorithms(SexRatioEntity data[]){

        Map<String, String> sortTimes = new HashMap<>();
        
        sortTimes.put("Insercao Direta", String.valueOf(insertionSort(data)));

//        sortTimes.put("Bubble Sort", String.valueOf(bubbleSort(data)));
//        sortTimes.put("Shell Sort", String.valueOf(shellSort(data)));

        return sortTimes;

    }



    private long insertionSort(SexRatioEntity data[]){

        long startTime = System.currentTimeMillis();

        for (int j = 1; j < data.length; j++) {
            SexRatioEntity key = data[j];
            int i = j-1;
            while ((i >= 0) && (data[i].getRatio() > key.getRatio())){
                data[i+1] = data[i];
                i--;
            }
            data[i+1] = key;
        }

        long elapsedTime = System.currentTimeMillis() - startTime;

        
        return elapsedTime;
    }

//    private long shellSort(int data[]){
//
//    }

//    private long bubbleSort(int data[]){

//    }

}
