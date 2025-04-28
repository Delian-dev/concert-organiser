package models;

public class Country {
    private final int countryId;
    private String countryName;
    private long population;

    public Country(int countryId, String countryName, long population) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.population = population;
    }

    public int getcountryId() {return countryId;}
    public String getCountry_name() {return countryName;}
    public long getPopulation() {return population;}

    public void setCountryName(String countryName) {this.countryName = countryName;}
    public void setPopulation(long population) {this.population = population;}

    @Override
    public String toString() {
        return "Country{" +
                "population=" + population +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
