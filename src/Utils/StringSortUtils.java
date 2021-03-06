package Utils;

import Entities.SexRatioEntity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Geovane on 02/10/2016.
 */
public class StringSortUtils extends AbstractSortUtils {

    /** Algoritimos para ordenação de arrays de strings **/

    /**
     * Todos os algoritimos de ordenação pelas strings leva em consideração o valor do somatórios dos ASCII da string conforme
     * conversado com o professor para não influenciar na complexidade do algoritimo, se não todos ficariam similares ao RadixSort.
     * Deste modo usamos o compareTo() para comparar as Strings
     **/


    @Override
    /** Inserção Direta Linear
     * Created by Fabricio on 30/08/2016.
     * FUNCIOMENTO: Implementado o Inserção direta com busca linear
     * MATERIAL DE APOIO: http://www.devmedia.com.br/algoritmos-de-ordenacao-em-java/32693
     */
    public long linearInsertionSort(SexRatioEntity data[]) {

        long startTime = System.currentTimeMillis();

        for (int j = 1; j < data.length; j++) {
            SexRatioEntity key = data[j];
            int i = j - 1;
            while ((i >= 0) && ((data[i].getCityName().compareTo(key.getCityName())) > 0)) {
                data[i + 1] = data[i];
                i--;
            }
            data[i + 1] = key;
        }

        long elapsedTime = System.currentTimeMillis() - startTime;


        return elapsedTime;
    }

    @Override
    /** Inserção Direta Busca Binária e funções auxiliares
     * Created by Fabrício on 30/09/2016.
     * FUNCIOMENTO: Implementado o Inserção direta com busca binária
     * MATERIAL DE APOIO:  http://stackoverflow.com/questions/3075752/binary-insertion-sort-algorithm
     */
    public long binaryInsertionSort(SexRatioEntity data[], int n) {

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; ++i) {
            SexRatioEntity temp = data[i];
            int left = 0;
            int right = i;
            while (left < right) {
                int middle = (left + right) / 2;
                if ((temp.getCityName().compareTo(data[middle].getCityName())) >= 0)
                    left = middle + 1;
                else
                    right = middle;
            }
            for (int j = i; j > left; --j) {
                swap(data, j - 1, j);
            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
    }


    /**
     * ShellSort
     * Created by Fabrício on 30/09/2016.
     * FUNCIONAMENTO: Foi implementado o ShellSort tradicional
     * MATERIAL DE APOIO: http://www.devmedia.com.br/algoritmos-de-ordenacao-em-java/32693
     */
    @Override
    public long shellSort(SexRatioEntity data[]) {

        long startTime = System.currentTimeMillis();

        int h = 1;
        int j;
        int n_elementos = data.length;
        SexRatioEntity c;
        while (h < n_elementos)
            h = h * 3 + 1;
        h = h / 3;
        while (h > 0) {
            for (int i = h; i < n_elementos; i++) {
                c = data[i];
                j = i;
                while (j >= h && ((data[j - h].getCityName().compareTo(c.getCityName())) > 0)) {
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


    /**
     * BubbleSort
     * Created by Fabrício on 30/09/2016.
     * FUNCIONAMENTO: Foi implementado o BubbleSort tradicional
     * MATERIAL DE APOIO: http://www.devmedia.com.br/algoritmos-de-ordenacao-em-java/32693
     */
    @Override
    public long bubbleSort(SexRatioEntity data[]) {

        long startTime = System.currentTimeMillis();

        int n = data.length;
        SexRatioEntity temp;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if ((data[j - 1].getCityName().compareTo(data[j].getCityName())) > 0) {
                    temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }

            }
        }
        creatDictionaryString(data,"CityCres.bin","CityDecres.bin");
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
    }


    /**
     * QuickSortRandomizado e funções auxiliares
     * Created by Fabrício on 30/09/2016.
     * FUNCIONAMENTO: Randomiza vetor atodo primeiramente e depois aplica o quickSort tradicional,sendo feito apartir de uma função para contagem do tempo.
     * MATERIAL DE APOIO: http://www.devmedia.com.br/algoritmos-de-ordenacao-em-java/32693
     */
    @Override
    public long quickSortRandom(SexRatioEntity[] data) {
        long startTime = System.currentTimeMillis();

        SexRatioEntity temporaryData;
        int posicao;
        for (int i = 0; i < data.length; i++) {
            Random gerador = new Random();
            posicao = gerador.nextInt(data.length - 1);
            temporaryData = data[i];
            data[i] = data[posicao];
            data[posicao] = temporaryData;
        }
        quickSortOperationToString(data, 0, data.length - 1);

        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
    }

    private static void quickSortOperationToString(SexRatioEntity[] data, int start, int fim) {
        if (start < fim) {
            int Point = discoveryPointToString(data, start, fim);
            quickSortOperationToString(data, start, Point - 1);
            quickSortOperationToString(data, Point + 1, fim);
        }
    }

    private static int discoveryPointToString(SexRatioEntity[] data, int start, int fim) {
        SexRatioEntity middle = data[start];
        int i = start + 1, f = fim;
        while (i <= f) {
            if ((data[i].getCityName().compareTo(middle.getCityName())) <= 0)
                i++;
            else if (middle.getCityName().compareTo(data[f].getCityName()) < 0)
                f--;
            else {
                SexRatioEntity temp = data[i];
                data[i] = data[f];
                data[f] = temp;
                i++;
                f--;
            }
        }
        data[start] = data[f];
        data[f] = middle;
        return f;
    }


    /**
     * MergeSort e funções auxiliares
     * Created by Fabrício on 30/09/2016.
     * FUNCIONAMENTO: Implementação do MergeSort tradicional, sendo feito apartir de uma função para contagem do tempo.
     * MATERIAL DE APOIO: http://stackoverflow.com/questions/13727030/mergesort-in-java
     */
    @Override
    public long mergeSort(SexRatioEntity[] data) {
        long startTime = System.currentTimeMillis();

        mergeSortAplication(data);

        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime;
    }

    private void mergeSortAplication(SexRatioEntity[] data) {
        if (data.length > 1) {
            int middle = data.length / 2;

            SexRatioEntity[] leftArray = Arrays.copyOfRange(data, 0, middle);
            SexRatioEntity[] rightArray = Arrays.copyOfRange(data, middle, data.length);

            mergeSortAplication(leftArray);
            mergeSortAplication(rightArray);

            merge(data, leftArray, rightArray);
        }
    }

    private void merge(SexRatioEntity[] data, SexRatioEntity[] leftData, SexRatioEntity[] rightData) {
        int totElem = leftData.length + rightData.length;
        int i, left_i, right_i;
        i = left_i = right_i = 0;
        while (i < totElem) {
            if ((left_i < leftData.length) && (right_i < rightData.length)) {
                if ((leftData[left_i].getCityName().compareTo(rightData[right_i].getCityName())) < 0) {
                    data[i] = leftData[left_i];
                    i++;
                    left_i++;
                } else {
                    data[i] = rightData[right_i];
                    i++;
                    right_i++;
                }
            } else {
                if (left_i >= leftData.length) {
                    while (right_i < rightData.length) {
                        data[i] = rightData[right_i];
                        i++;
                        right_i++;
                    }
                }
                if (right_i >= rightData.length) {
                    while (left_i < leftData.length) {
                        data[i] = leftData[left_i];
                        left_i++;
                        i++;
                    }
                }
            }
        }
    }

    @Override
    public long heapSort(SexRatioEntity[] data) {

        SexRatioEntity[] dataClone = data.clone();

        long startTime = System.currentTimeMillis();

        SexRatioEntity[] heap = buildheapToString(dataClone);

        int n = heap.length - 1;

        for (int i = n; i > 0; i--) {
            SexRatioEntity aux = heap[0];
            heap[0] = heap[i];
            heap[i] = aux;
            n = n - 1;
            heap = maxheapToString(heap, 0, n);
        }


        long elapsedTime = System.currentTimeMillis() - startTime;

        return elapsedTime;
    }


    private SexRatioEntity[] buildheapToString(SexRatioEntity[] data) {

        int n = data.length - 1;
        for (int i = n / 2; i >= 0; i--) {
            data = maxheapToString(data, i, n);
        }

        return data;
    }

    private SexRatioEntity[] maxheapToString(SexRatioEntity[] data, int i, int buildHeapSize) {

        int left = 2 * i;
        int right = (2 * i) + 1;

        int largest;

        if (left <= buildHeapSize && data[left].getCityName().compareTo(data[i].getCityName()) >= 0) {
            largest = left;
        } else {
            largest = i;
        }

        if (right <= buildHeapSize && data[right].getCityName().compareTo(data[largest].getCityName()) >= 0) {
            largest = right;
        }
        if (largest != i) {
            SexRatioEntity aux = data[i];
            data[i] = data[largest];
            data[largest] = aux;
            data = maxheapToString(data, largest, buildHeapSize);
        }

        return data;
    }

    @Override
    public long radixSort(SexRatioEntity[] data) throws Exception {
        //Must be implemented

        return 0;
    }

    public void creatDictionaryString(SexRatioEntity[] data, String nameFileCrescente, String nameFileDecrescente){
        try{
            FileOutputStream fileOS = new FileOutputStream(nameFileCrescente);
            ObjectOutputStream os = new ObjectOutputStream(fileOS);
            for(int i=0 ; i<data.length ; i++){
                os.writeInt(data[i].getChave());
            }
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            FileOutputStream fileOS = new FileOutputStream(nameFileDecrescente);
            ObjectOutputStream os = new ObjectOutputStream(fileOS);
            for(int i=(data.length-1) ; i>=0 ; i--){
                os.writeInt(data[i].getChave());
            }
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
