import Entities.SexRatioEntity;
import Utils.SortUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Geovane on 17/08/2016.
 */
public class Main {

    //public static final String CSV_FILE_PATH = "C:\\BitBucket\\CPD\\CPDProject\\resources\\database.csv";
    public static final String PERSISTENCE_FILE_NAME = "resultados_Iluminados.txt";
    public static final int UNLIMITED_ELEMENTES_FLAG = -1;
    public static final String CSV_FILE_PATH = "C:\\Users\\Fabricio Szczesny\\Documents\\CPD\\CPDProject\\resources\\database.csv";
//    public SexRatioEntity[] data;
/*
    public void startSort(){

        SexRatioEntity[] data = loadInfo(UNLIMITED_ELEMENTES_FLAG);
        SortUtils utils = new SortUtils();
        SexRatioEntity[] sortedData = utils.getElementsSorted(data);


    }*/

    private void calculateSortTimes(){
        SexRatioEntity[] data_with_100 = loadInfo(100);
        SexRatioEntity[] data_with_1000 = loadInfo(1000);
        SexRatioEntity[] data_with_10000 = loadInfo(10000);

        Map<String, String> algorithmsTimeWith100 = new HashMap<>();
        Map<String, String> algorithmsTimeWith1000 = new HashMap<>();
        Map<String, String> algorithmsTimeWith10000 = new HashMap<>();
        SortUtils utils = new SortUtils();

        algorithmsTimeWith100 = utils.sortByAllAlgorithms(data_with_100);
        algorithmsTimeWith1000 = utils.sortByAllAlgorithms(data_with_1000);
        algorithmsTimeWith10000 = utils.sortByAllAlgorithms(data_with_10000);

        Map<String, String>[] algorithmsTimes = new Map[3];

        algorithmsTimes[0] = algorithmsTimeWith100;
        algorithmsTimes[1] = algorithmsTimeWith1000;
        algorithmsTimes[2] = algorithmsTimeWith10000;

        System.out.println("Tempo decorrido para cada algoritmo com 100 elementos (em milissegundos) :");
        System.out.println(algorithmsTimeWith100.toString());
        System.out.println("Tempo decorrido para cada algoritmo com 1000 elementos (em milissegundos) :");
        System.out.println(algorithmsTimeWith1000.toString());
        System.out.println("Tempo decorrido para cada algoritmo com 10000 elementos (em milissegundos) :");
        System.out.println(algorithmsTimeWith10000.toString());

        persistenceResults(algorithmsTimes);
    }

    private SexRatioEntity[] loadInfo(int maxFileSize){

        File csvFile = new File(CSV_FILE_PATH);
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));

            int fileSize = countLines(CSV_FILE_PATH)-1;

//            System.out.println("Número de entidades do arquivo " + fileSize);


            //Relevant infos position from csv: 2 - UF; 3 - City; 5 - Male; 6 - Female; 7 - Ratio
            //Uses readLine for the first time to remove the header
            String currentInfo = br.readLine();

            if(fileSize > maxFileSize && maxFileSize != UNLIMITED_ELEMENTES_FLAG){
                fileSize = maxFileSize;
            }

            SexRatioEntity[] data = new SexRatioEntity[fileSize];

            for (int i = 0; i < fileSize; i++) {

//                System.out.println("Lendo a linha numero: " + (i+1));

                currentInfo = br.readLine();
                SexRatioEntity currentEntity = new SexRatioEntity();

                String splitedEntity[] = currentInfo.split(",");

                currentEntity.setUf(splitedEntity[2]);
                currentEntity.setCityName(splitedEntity[3]);
                currentEntity.setMalePopulation(Double.parseDouble(splitedEntity[5]));
                currentEntity.setFemalePopulation(Double.parseDouble(splitedEntity[6]));
                currentEntity.setRatio(Double.parseDouble(splitedEntity[7]));

                data[i] = currentEntity;

            }
            return data;

        }catch (FileNotFoundException fnfe) {
            System.out.println("File not found, please verify the file path - " + fnfe.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }


    private void persistenceResults(Map<String, String>[] algorithTimes){

        try {
            PrintWriter writer = new PrintWriter(PERSISTENCE_FILE_NAME, "UTF-8");
            //i=0, elements number = 100
            //i=1, elements number = 1000
            //i=2, elements number = 10000
            writer.append(SortUtils.INSERTION_SORT_LINEAR_KEY + ", numerico, 100, " + algorithTimes[0].get(SortUtils.INSERTION_SORT_LINEAR_KEY) + "\n");
            writer.append(SortUtils.INSERTION_SORT_LINEAR_KEY + ", numerico, 1000, " + algorithTimes[1].get(SortUtils.INSERTION_SORT_LINEAR_KEY) + "\n");
            writer.append(SortUtils.INSERTION_SORT_LINEAR_KEY + ", numerico, 10000, " + algorithTimes[2].get(SortUtils.INSERTION_SORT_LINEAR_KEY) + "\n");

            writer.append(SortUtils.INSERTION_SORT_BINARY_KEY + ", numerico, 100, " + algorithTimes[0].get(SortUtils.INSERTION_SORT_BINARY_KEY) + "\n");
            writer.append(SortUtils.INSERTION_SORT_BINARY_KEY + ", numerico, 1000, " + algorithTimes[1].get(SortUtils.INSERTION_SORT_BINARY_KEY) + "\n");
            writer.append(SortUtils.INSERTION_SORT_BINARY_KEY + ", numerico, 10000, " + algorithTimes[2].get(SortUtils.INSERTION_SORT_BINARY_KEY) + "\n");

            writer.append(SortUtils.BUBBLE_SORT_KEY + ", numerico, 100, " + algorithTimes[0].get(SortUtils.BUBBLE_SORT_KEY) + "\n");
            writer.append(SortUtils.BUBBLE_SORT_KEY + ", numerico, 1000, " + algorithTimes[1].get(SortUtils.BUBBLE_SORT_KEY) + "\n");
            writer.append(SortUtils.BUBBLE_SORT_KEY + ", numerico, 10000, " + algorithTimes[2].get(SortUtils.BUBBLE_SORT_KEY) + "\n");

            writer.append(SortUtils.SHELL_SORT_KEY + ", numerico, 100, " + algorithTimes[0].get(SortUtils.SHELL_SORT_KEY) + "\n");
            writer.append(SortUtils.SHELL_SORT_KEY + ", numerico, 1000, " + algorithTimes[1].get(SortUtils.SHELL_SORT_KEY) + "\n");
            writer.append(SortUtils.SHELL_SORT_KEY + ", numerico, 10000, " + algorithTimes[2].get(SortUtils.SHELL_SORT_KEY) + "\n");

//            writer.append(SortUtils.INSERTION_SORT_LINEAR_KEY + ", numerico, 100, " + algorithTimes[0].get(SortUtils.INSERTION_SORT_LINEAR_KEY) + "\n");
//            writer.append(SortUtils.INSERTION_SORT_LINEAR_KEY + ", numerico, 1000, " + algorithTimes[1].get(SortUtils.INSERTION_SORT_LINEAR_KEY) + "\n");
//            writer.append(SortUtils.INSERTION_SORT_LINEAR_KEY + ", numerico, 10000, " + algorithTimes[2].get(SortUtils.INSERTION_SORT_LINEAR_KEY) + "\n");

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void persistenceElements(SexRatioEntity[] elements){

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream("binarySorted"));
            for (SexRatioEntity element: elements) {
                out.write(elements[0].getCityName().getBytes(), 0 ,elements[0].getCityName().length());
                //write here more of the info
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String args[]){
        Main caller = new Main();
       // caller.startSort();
        caller.calculateSortTimes();
    }

}
