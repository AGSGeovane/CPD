package Utils;

import Entities.SexRatioEntity;

/**
 * Created by Geovane on 02/10/2016.
 */
public abstract class AbstractSortUtils {

    public abstract long linearInsertionSort(SexRatioEntity data[]);

    public abstract long binaryInsertionSort(SexRatioEntity data[], int n);

    public abstract long shellSort(SexRatioEntity data[]);

    public abstract long bubbleSort(SexRatioEntity data[]);

    public abstract long quickSortRandom(SexRatioEntity data[]);

    public abstract long mergeSort(SexRatioEntity data[]);

    public abstract long heapSort(SexRatioEntity data[]);

    public abstract long radixSort(SexRatioEntity data[]) throws Exception;

    public void swap(SexRatioEntity a[], int i, int j) {
        SexRatioEntity k = a[i];
        a[i] = a[j];
        a[j] = k;
    }

}
