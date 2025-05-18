package models;

public class Country {
    private int countryId;
    private String countryName;
    private long population;

    public Country(int countryId, String countryName, long population) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.population = population;
    }

    public Country(String countryName, long population) {
        this.countryName = countryName;
        this.population = population;
    }

    public int getCountryId() {return countryId;}
    public String getCountry_name() {return countryName;}
    public long getPopulation() {return population;}

    public void setCountryName(String countryName) {this.countryName = countryName;}
    public void setPopulation(long population) {this.population = population;}

    @Override
    public String toString() {
//        return "Country{" +
//                "countryId=" + countryId +
//                ", population=" + population +
//                ", countryName='" + countryName + '\'' +
//                '}';
        return countryName;
    }
}
