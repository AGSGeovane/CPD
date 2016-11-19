package Persistence;

import Entities.SexRatioEntity;
import Controller.SortController;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Geovane on 02/10/2016 and edited by Fabricio on 15/11/2016.
 */
public class SortDAO {

    public static final String FILE_RELATIVE_PATH = "database.txt";
    public static final String PERSISTENCE_FILE_NAME = "resultados_Iluminados.txt";
    public static final String FILE_BASE_PATH = "base.bin";
    public static final int UNLIMITED_ELEMENTES_FLAG = -1;

    public SexRatioEntity[] loadInfo(int maxFileSize) throws Exception {
        if (maxFileSize > 0) {
            return loadInfo(maxFileSize, true);
        } else {
            throw new Exception("Número de elementos requerido não é válido, por favor informe um número maior que 0 --- maxFileSize = " + maxFileSize);
        }

    }


    private SexRatioEntity[] loadInfo(int maxFileSize, boolean isValidSize) {

        try {

            ClassLoader classLoader = getClass().getClassLoader();
            File databaseFile = new File(classLoader.getResource(FILE_RELATIVE_PATH).getFile());

            BufferedReader br = new BufferedReader(new FileReader(databaseFile));

            int fileSize = countLines(databaseFile.getAbsolutePath()) - 1;

            //Relevant infos position from csv: 2 - UF; 3 - City; 5 - Male; 6 - Female; 7 - Ratio
            //Uses readLine for the first time to remove the header
            String currentInfo = br.readLine();

            if (fileSize > maxFileSize && maxFileSize != UNLIMITED_ELEMENTES_FLAG) {
                fileSize = maxFileSize;
            }

            SexRatioEntity[] data = new SexRatioEntity[fileSize];
            DataOutputStream out = openOutputStream(FILE_BASE_PATH);
            for (int i = 0; i < fileSize; i++) {

                currentInfo = br.readLine();
                SexRatioEntity currentEntity = new SexRatioEntity();

                String splitedEntity[] = currentInfo.split(",");

                currentEntity.setChave(i + 1);
                currentEntity.setUf(splitedEntity[2]);
                currentEntity.setCityName(splitedEntity[3]);
                currentEntity.setMalePopulation(Double.parseDouble(splitedEntity[5]));
                currentEntity.setFemalePopulation(Double.parseDouble(splitedEntity[6]));
                currentEntity.setRatio(Double.parseDouble(splitedEntity[7]));
                data[i] = currentEntity;
                writeData(currentEntity, out);
            }
            out.close();
            return data;

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found, please verify the file path - " + fnfe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataOutputStream openOutputStream(String name) throws Exception {
        DataOutputStream out = null;
        File file = new File(name);
        out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        return out;
    }

    private static DataInputStream openInputStream(String name) throws Exception {
        DataInputStream out = null;
        File file = new File(name);
        out = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        return out;
    }

    private static void writeData(SexRatioEntity data, DataOutputStream out) throws Exception {
        out.writeInt(data.getChave());
        out.writeDouble(data.getFemalePopulation());
        out.writeDouble(data.getMalePopulation());
        out.writeDouble(data.getRatio());
        out.writeUTF(data.getCityName());
    }

    private static SexRatioEntity ReadData(DataInputStream out) throws Exception {
        SexRatioEntity data = new SexRatioEntity();
        data.setChave(out.readInt());
        data.setFemalePopulation(out.readDouble());
        data.setMalePopulation(out.readDouble());
        data.setRatio(out.readDouble());
        data.setCityName(out.readUTF());
        return data;
    }

    public SexRatioEntity sharedItemOnBase( int codigo ){

        /**
         * If return NULL the chave don't exist because the file end. Else return a SexRatioEntity with all datas of the city that have this key
         */

        SexRatioEntity dataAux = new SexRatioEntity();
        try {
            DataInputStream in = openInputStream(FILE_BASE_PATH);
            do {
                dataAux = ReadData(in);
            }while (dataAux.getChave() != codigo);
            in.close();
        }
        catch (FileNotFoundException fnfe){
            System.out.println("File not found, please verify the file path - " + fnfe.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dataAux;
    }

    public SexRatioEntity[] readDictionary( String fileNameDictionary ,int size){
        SexRatioEntity[] data = new SexRatioEntity[size];
        try {
            DataInputStream is = openInputStream(fileNameDictionary);
            for (int i=0; i<size ; i++){
                int code = is.readInt();
                data[i] = sharedItemOnBase( code );
            }
            is.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public void dispayData(SexRatioEntity[] data, int size){
        for(int i=0 ; i<size ; i++){
            System.out.print("Cidade número: ");
            System.out.println((size+1));
            System.out.print("Nome da Cidade: ");
            System.out.println(data[i].getCityName());
            System.out.print("População Masculina: ");
            System.out.println(data[i].getMalePopulation());
            System.out.print("População Feminina: ");
            System.out.println(data[i].getFemalePopulation());
            System.out.print("Razão entre as populações: ");
            System.out.println(data[i].getRatio());
            System.out.println("");
        }
    }

    public void interfaceWithUser(){
        System.out.print("Selecione a operação sobre a base dejesada:");
        System.out.println("1 - Ordem Crescente pela População Masculina.");
        System.out.println("2 - Ordem Crescente pela População Feminina.");
        System.out.println("3 - Ordem Crescente pela Razão entre População Masculina e Feminina.");
        System.out.println("4 - Ordem Crescente pelo Nome da Cidade.");
        System.out.println("5 - Ordem Decrescente pela População Masculina.");
        System.out.println("6 - Ordem Decrescente pela População Feminina.");
        System.out.println("7 - Ordem Decrescente pela Razão entre População Masculina e Feminina.");
        System.out.println("8 - Ordem Decrescente pelo Nome da Cidade.");
        System.out.println("9 - Exit.");
        Scanner dados = new Scanner(System.in);
        int option = dados.nextInt();
        if((option>0)&&(option<9)){
            System.out.print("Informe o número de elementos a serem mostrados");
            int numberOfElements = dados.nextInt();
            if((numberOfElements<10000)&&(numberOfElements>0)){
                SexRatioEntity[] data = new SexRatioEntity[numberOfElements];
                switch (option){
                    case 1:
                        data = readDictionary("MaleCres.bin", numberOfElements);
                        break;
                    case 2:
                        data = readDictionary("FemaleCres.bin", numberOfElements);
                        break;
                    case 3:
                        data = readDictionary("RatioCres.bin", numberOfElements);
                        break;
                    case 4:
                        data = readDictionary("CityCres.bin", numberOfElements);
                        break;
                    case 5:
                        data = readDictionary("MaleDecres.bin", numberOfElements);
                        break;
                    case 6:
                        data = readDictionary("FemaleDecres.bin", numberOfElements);
                        break;
                    case 7:
                        data = readDictionary("RatioDecres.bin", numberOfElements);
                        break;
                    case 8:
                        data = readDictionary("CityDecres.bin", numberOfElements);
                        break;
                }
                dispayData(data, numberOfElements);
            }
            else {
                System.out.println("Número de elementos selecionados inválido.");
            }
        } else if (option == 9) {
            System.out.println("Você selecionou sair.");
        }
        else if(option<1 || option>9){
            System.out.println("Opção selecionada inválida.");
        }
    }

    public void persistenceResults(Map<String, String>[] algorithTimes) {

        try {
            PrintWriter writer = new PrintWriter(PERSISTENCE_FILE_NAME, "UTF-8");
            //i=0, elements number = 100
            //i=1, elements number = 1000
            //i=2, elements number = 10000

            //'ISBL', 'numerico', elements, time
            writer.append(SortController.INSERTION_SORT_LINEAR_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.INSERTION_SORT_LINEAR_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_LINEAR_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.INSERTION_SORT_LINEAR_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_LINEAR_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.INSERTION_SORT_LINEAR_KEY + SortController.NUMERIC_SUFIX) + "\n\n");

            //'ISBL', 'categorico', elements, time
            writer.append(SortController.INSERTION_SORT_LINEAR_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.INSERTION_SORT_LINEAR_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_LINEAR_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.INSERTION_SORT_LINEAR_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_LINEAR_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.INSERTION_SORT_LINEAR_KEY + SortController.STRING_SUFIX) + "\n\n\n\n");


            //'ISBB', 'numerico', elements, time
            writer.append(SortController.INSERTION_SORT_BINARY_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.INSERTION_SORT_BINARY_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_BINARY_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.INSERTION_SORT_BINARY_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_BINARY_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.INSERTION_SORT_BINARY_KEY + SortController.NUMERIC_SUFIX) + "\n\n");

            //'ISBB', 'categorico', elements, time
            writer.append(SortController.INSERTION_SORT_BINARY_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.INSERTION_SORT_BINARY_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_BINARY_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.INSERTION_SORT_BINARY_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.INSERTION_SORT_BINARY_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.INSERTION_SORT_BINARY_KEY + SortController.STRING_SUFIX) + "\n\n\n");


            //'BBST', 'numerico', elements, time
            writer.append(SortController.BUBBLE_SORT_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.BUBBLE_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.BUBBLE_SORT_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.BUBBLE_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.BUBBLE_SORT_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.BUBBLE_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n\n");

            //'BBST', 'categorico', elements, time
            writer.append(SortController.BUBBLE_SORT_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.BUBBLE_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.BUBBLE_SORT_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.BUBBLE_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.BUBBLE_SORT_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.BUBBLE_SORT_KEY + SortController.STRING_SUFIX) + "\n\n\n\n");


            //'SHST', 'numerico', elements, time
            writer.append(SortController.SHELL_SORT_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.SHELL_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.SHELL_SORT_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.SHELL_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.SHELL_SORT_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.SHELL_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n\n");

            //'SHST', 'categorico', elements, time
            writer.append(SortController.SHELL_SORT_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.SHELL_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.SHELL_SORT_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.SHELL_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.SHELL_SORT_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.SHELL_SORT_KEY + SortController.STRING_SUFIX) + "\n\n\n\n");


            //'QSRM', 'numerico', elements, time
            writer.append(SortController.QUICK_SORT_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.QUICK_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.QUICK_SORT_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.QUICK_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.QUICK_SORT_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.QUICK_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n\n");

            //'QSRM', 'categorico', elements, time
            writer.append(SortController.QUICK_SORT_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.QUICK_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.QUICK_SORT_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.QUICK_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.QUICK_SORT_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.QUICK_SORT_KEY + SortController.STRING_SUFIX) + "\n\n\n\n");


            //'MGST', 'numerico', elements, time
            writer.append(SortController.MERGE_SORT_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.MERGE_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.MERGE_SORT_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.MERGE_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.MERGE_SORT_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.MERGE_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n\n");

            //'MGST', 'categorico', elements, time
            writer.append(SortController.MERGE_SORT_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.MERGE_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.MERGE_SORT_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.MERGE_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.MERGE_SORT_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.MERGE_SORT_KEY + SortController.STRING_SUFIX) + "\n\n\n\n");


            //'HPST', 'numerico', elements, time
            writer.append(SortController.HEAP_SORT_KEY + SortController.NUMERIC_SUFIX + ", 100, " + algorithTimes[0].get(SortController.HEAP_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.HEAP_SORT_KEY + SortController.NUMERIC_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.HEAP_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n");
            writer.append(SortController.HEAP_SORT_KEY + SortController.NUMERIC_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.HEAP_SORT_KEY + SortController.NUMERIC_SUFIX) + "\n\n");
//
            //'HPST', 'categorico', elements, time
            writer.append(SortController.HEAP_SORT_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.HEAP_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.HEAP_SORT_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.HEAP_SORT_KEY + SortController.STRING_SUFIX) + "\n");
            writer.append(SortController.HEAP_SORT_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.HEAP_SORT_KEY + SortController.STRING_SUFIX) + "\n\n\n\n");


//            //'RMSD', 'categorico', elements, time
//            writer.append(SortController.RADIX_SORT_KEY + SortController.STRING_SUFIX + ", 100, " + algorithTimes[0].get(SortController.RADIX_SORT_KEY + SortController.STRING_SUFIX) + "\n");
//            writer.append(SortController.RADIX_SORT_KEY + SortController.STRING_SUFIX + ", 1000, " + algorithTimes[1].get(SortController.RADIX_SORT_KEY + SortController.STRING_SUFIX) + "\n");
//            writer.append(SortController.RADIX_SORT_KEY + SortController.STRING_SUFIX + ", 10000, " + algorithTimes[2].get(SortController.RADIX_SORT_KEY + SortController.STRING_SUFIX) + "\n");

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private int countLines(String filename) throws IOException {
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

}
