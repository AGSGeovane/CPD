import Entities.SexRatioEntity;

import java.io.*;

/**
 * Created by Geovane on 17/08/2016.
 */
public class Main {

    public static final String CSV_FILE_PATH = "C:\\BitBucket\\CPD\\CPDProject\\resources\\database_test.csv";
    public SexRatioEntity[] data;


    public void startSort(){

        loadInfo();

        for (SexRatioEntity entity: data ) {
            System.out.println("Entity: " + entity.toString() + "\n");
        }


    }

    private void loadInfo(){

        File csvFile = new File(CSV_FILE_PATH);
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));

            int fileSize = countLines(CSV_FILE_PATH)-1;

//            System.out.println("Número de entidades do arquivo " + fileSize);

            data = new SexRatioEntity[fileSize];

            //Relevant infos position from csv: 2 - UF; 3 - City; 5 - Male; 6 - Female; 7 - Ratio
            //Uses readLine for the first time to remove the header
            String currentInfo = br.readLine();

            for (int i = 0; i < fileSize; i++) {

                System.out.println("Lendo a linha numero: " + (i+1));

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


        }catch (FileNotFoundException fnfe) {
            System.out.println("File not found, please verify the file path - " + fnfe.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }


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

    public static void main(String args[]){
        Main caller = new Main();
        caller.startSort();
    }

}
