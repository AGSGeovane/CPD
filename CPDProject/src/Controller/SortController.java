package Controller;

import Entities.SexRatioEntity;
import Persistence.SortDAO;
import Utils.AbstractSortUtils;
import Utils.NumericSortUtils;
import Utils.StringSortUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;

/**
 * Created by Mordor on 20/08/2016.
 */
public class SortController {

    public static final String INSERTION_SORT_LINEAR_KEY = "ISBL";
    public static final String INSERTION_SORT_BINARY_KEY = "ISBB";
    public static final String BUBBLE_SORT_KEY = "BBST";
    public static final String SHELL_SORT_KEY = "SHST";
    public static final String QUICK_SORT_KEY = "QSRM";
    public static final String MERGE_SORT_KEY = "MGST";
    public static final String HEAP_SORT_KEY = "HPST";
    public static final String RADIX_SORT_KEY = "RMSD";

    public static final String STRING_SUFIX = ", categorico";
    public static final String NUMERIC_SUFIX = ", numerico";

    private AbstractSortUtils stringSorters = new StringSortUtils();
    private AbstractSortUtils numericSorters = new NumericSortUtils();

    public void start() throws Exception {

        SortDAO persistence = new SortDAO();

        SexRatioEntity[] data_with_100 = persistence.loadInfo(100);
        SexRatioEntity[] data_with_1000 = persistence.loadInfo(1000);
        SexRatioEntity[] data_with_10000 = persistence.loadInfo(10000);


        Map<String, String> algorithmsTimeWith100 = new HashMap<>();
        Map<String, String> algorithmsTimeWith1000 = new HashMap<>();
        Map<String, String> algorithmsTimeWith10000 = new HashMap<>();

        algorithmsTimeWith100 = sortByAllAlgorithms(data_with_100);
        algorithmsTimeWith1000 = sortByAllAlgorithms(data_with_1000);
        algorithmsTimeWith10000 = sortByAllAlgorithms(data_with_10000);

        Map<String, String>[] algorithmsTimes = new Map[3];

        algorithmsTimes[0] = algorithmsTimeWith100;
        algorithmsTimes[1] = algorithmsTimeWith1000;
        algorithmsTimes[2] = algorithmsTimeWith10000;

        persistence.persistenceResults(algorithmsTimes);

    }


    public Map<String, String> sortByAllAlgorithms(SexRatioEntity data[]) {

        Map<String, String> sortTimes = new HashMap<>();

        sortTimes.put(INSERTION_SORT_LINEAR_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.linearInsertionSort(data))); //Insertion Sort com Busca Linear Numerico
        sortTimes.put(INSERTION_SORT_LINEAR_KEY + STRING_SUFIX, String.valueOf(stringSorters.linearInsertionSort(data))); //Insertion Sort com Busca Linear Categorico

        sortTimes.put(INSERTION_SORT_BINARY_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.binaryInsertionSort(data, data.length))); //Insertion Sort com Busca Binária Numerico
        sortTimes.put(INSERTION_SORT_BINARY_KEY + STRING_SUFIX, String.valueOf(stringSorters.binaryInsertionSort(data, data.length))); //Insertion Sort com Busca Binária Categorico

        sortTimes.put(BUBBLE_SORT_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.bubbleSort(data))); //Bubble Sort Numerico
        sortTimes.put(BUBBLE_SORT_KEY + STRING_SUFIX, String.valueOf(stringSorters.bubbleSort(data))); //Bubble Sort Categorico

        sortTimes.put(SHELL_SORT_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.shellSort(data))); //Shell Sort Numerico
        sortTimes.put(SHELL_SORT_KEY + STRING_SUFIX, String.valueOf(stringSorters.shellSort(data))); //Shell Sort Categorico

        sortTimes.put(QUICK_SORT_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.quickSortRandom(data))); //Quick Sort Randomizado Numerico
        sortTimes.put(QUICK_SORT_KEY + STRING_SUFIX, String.valueOf(stringSorters.quickSortRandom(data))); //Quick Sort Randomizado Categorico

        sortTimes.put(MERGE_SORT_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.mergeSort(data))); //Merge Sort Numerico
        sortTimes.put(MERGE_SORT_KEY + STRING_SUFIX, String.valueOf(stringSorters.mergeSort(data))); //Merge Sort Categorico

        sortTimes.put(HEAP_SORT_KEY + NUMERIC_SUFIX, String.valueOf(numericSorters.heapSort(data))); //Heap Sort Numerico
        sortTimes.put(HEAP_SORT_KEY + STRING_SUFIX, String.valueOf(stringSorters.heapSort(data))); //Heap Sort Categorico

        try {
            sortTimes.put(RADIX_SORT_KEY + STRING_SUFIX, String.valueOf(stringSorters.radixSort(data))); //Radix Sort
        } catch (Exception e) {
            System.out.println(e);
        }


        return sortTimes;

    }


    private long radixSort(SexRatioEntity[] data) {

        long startTime = System.currentTimeMillis();

        String t = new String();

        int i;
        String firstElement = data[0].getCityName();
        int exp = 1;
        int length = data.length;


        long elapsedTime = startTime - System.currentTimeMillis();

        return elapsedTime;
    }


    public static void sort(int[] a) {
        int i, m = a[0], exp = 1, n = a.length;
        int[] b = new int[10];
        for (i = 1; i < n; i++)
            if (a[i] > m)
                m = a[i];
        while (m / exp > 0) {
            int[] bucket = new int[10];

            for (i = 0; i < n; i++)
                bucket[(a[i] / exp) % 10]++;
            for (i = 1; i < 10; i++)
                bucket[i] += bucket[i - 1];
            for (i = n - 1; i >= 0; i--)
                b[--bucket[(a[i] / exp) % 10]] = a[i];
            for (i = 0; i < n; i++)
                a[i] = b[i];
            exp *= 10;
        }
    }
}
