package Utils;

import Entities.SexRatioEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mordor on 20/08/2016.
 */
public class SortUtils {

    private SexRatioEntity[] dataToQuickSort;

    public Map<String, String> sortByAllAlgorithms(SexRatioEntity data[]){

        Map<String, String> sortTimes = new HashMap<>();
        
        sortTimes.put("Insercao Direta com Busca Linear", String.valueOf(linearInsertionSort(data)));
        sortTimes.put("Insercao Direta com Busca Binária", String.valueOf(binaryInsertionSort(data, data.length)));
        sortTimes.put("Bubble Sort", String.valueOf(bubbleSort(data)));
        sortTimes.put("Shell Sort", String.valueOf(shellSort(data)));
        sortTimes.put("Quick Sort", String.valueOf(quickSort(data)));

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

    private long bubbleSort(SexRatioEntity data[]){

        long startTime = System.currentTimeMillis();

        int n = data.length;
        SexRatioEntity temp;

        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){

                if(data[j-1].getRatio() > data[j].getRatio()){
                    temp = data[j-1];
                    data[j-1] = data[j];
                    data[j] = temp;
                }

            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
    }

    private long quickSort(SexRatioEntity data[]){

        dataToQuickSort = data;

        long startTime = System.currentTimeMillis();

//        System.out.println("Unordered Data: \n\n");
//        for (SexRatioEntity unorderedData:dataToQuickSort) {
//            System.out.println(unorderedData.getCityName() + " || " + unorderedData.getRatio());
//        }

        quickSort(0, dataToQuickSort.length-1);

        long elapsedTime = System.currentTimeMillis() - startTime;


//        System.out.println("\n\n\nOrdered Data: \n");
//        for (SexRatioEntity quickData : dataToQuickSort){
//            System.out.println(quickData.getCityName() + " || " + quickData.getRatio());
//        }

        return elapsedTime;

    }

    private void quickSort( int firstIndex, int lastIndex){
        if(lastIndex > firstIndex){
            int pivot = randomPart(firstIndex, lastIndex);



            quickSort(firstIndex, pivot - 1);
            quickSort(pivot + 1, lastIndex);
        }
    }

    private int randomPart(int firstIndex, int lastIndex) {
            int indexToBeTrade = (firstIndex + 1) + (int)(Math.random() * (lastIndex - firstIndex - 1));

//        System.out.println("First Index, Last index, Index to be trade: " + firstIndex + "||" + lastIndex + "||" + indexToBeTrade);
//
//            SexRatioEntity aux = dataToQuickSort[firstIndex];
//            dataToQuickSort[firstIndex] = dataToQuickSort[indexToBeTrade];
//            dataToQuickSort[indexToBeTrade] = aux;

            return partition(firstIndex, lastIndex);
    }

    private int partition(int firstIndex, int lastIndex){

        int i = firstIndex, j = lastIndex;
        SexRatioEntity aux;
        SexRatioEntity pivot = dataToQuickSort[(firstIndex + lastIndex) / 2];

        while (i <= j) {
            while (dataToQuickSort[i].getRatio() < pivot.getRatio())
                i++;
            while (dataToQuickSort[j].getRatio() > pivot.getRatio())
                j--;
            if (i <= j) {
                aux = dataToQuickSort[i];
                dataToQuickSort[i] = dataToQuickSort[j];
                dataToQuickSort[j] = aux;
                i++;
                j--;
            }
        }

        return i;
    }
    
}
