package models;

public class Location {
    private final int locationId;
    private int countryId;
    private String city;
    private String address;

    public Location(int locationId, int countryId, String city, String address) {
        this.locationId = locationId;
        this.countryId = countryId;
        this.city = city;
        this.address = address;
    }

    public int getlocationId() { return locationId; }
    public int getCountryId() { return countryId; }
    public String getCity() { return city; }
    public String getAddress() { return address; }

    public void setCountryId(int countryId) { this.countryId = countryId; }
    public void setCity(String city) { this.city = city; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Location{" +
                "countryId=" + countryId +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
