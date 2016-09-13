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
        
        sortTimes.put("Insercao Direta com Busca Linear", String.valueOf(linearInsertionSort(data)));
        sortTimes.put("Insercao Direta com Busca Binária", String.valueOf(binaryInsertionSort(data, data.length)));
//        sortTimes.put("Bubble Sort", String.valueOf(bubbleSort(data)));
        sortTimes.put("Shell Sort", String.valueOf(shellSort(data)));

        return sortTimes;

    }



    private long linearInsertionSort(SexRatioEntity data[]){

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

    private long binaryInsertionSort(SexRatioEntity data[], int n){

        long startTime = System.currentTimeMillis();
        for (int i=0;i<n;++i){
            SexRatioEntity temp = data[i];
            int left = 0;
            int right = i;
            while (left < right){
                int middle = (left + right)/2;
                if (temp.getRatio() >= data[middle].getRatio())
                    left = middle+1;
                else
                    right = middle;
            }
            for (int j=i ; j>left ; --j){
               swap(data,j-1,j);
            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
    }

    public static void swap(SexRatioEntity a[],int i,int j){
        SexRatioEntity k=a[i];
        a[i]=a[j];
        a[j]=k;
    }

    private long shellSort(SexRatioEntity data[]){

        long startTime = System.currentTimeMillis();

         int h = 1;
         int j;
         int n_elementos = data.length;
         SexRatioEntity c;
         while(h < n_elementos)
            h = h * 3 + 1;
         h = h / 3;
	     while (h > 0) {
             for (int i = h; i < n_elementos; i++) {
                 c = data[i];
                 j = i;
                 while (j >= h && data[j - h].getRatio() > c.getRatio()) {
                    data[j] = data[j - h];
                    j = j - h;
                 }
                 data[j] = c;
             }
         h = h / 2;
    }
    long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
}

//    private long bubbleSort(int data[]){

//    }

}
