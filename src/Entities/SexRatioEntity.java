package Entities;

/**
 * Created by Geovane on 17/08/2016.
 */
public class SexRatioEntity {


    private String cityName;
    private String uf;
    private double malePopulation;
    private double femalePopulation;
    private double ratio;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public double getMalePopulation() {
        return malePopulation;
    }

    public void setMalePopulation(double malePopulation) {
        this.malePopulation = malePopulation;
    }

    public double getFemalePopulation() {
        return femalePopulation;
    }

    public void setFemalePopulation(double femalePopulation) {
        this.femalePopulation = femalePopulation;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String toString(){
        return "Cidade: " + cityName + "\n" +
                "Estado: " + uf + "\n" +
                "População Masculina: " + malePopulation + "\n" +
                "População Feminina: " + femalePopulation + "\n" +
                "Razão M/F: " + ratio;
    }
}
