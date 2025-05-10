package models;

public class Concert {
    private final int concertId;
    private int locationId;
    private String concertName;
    private String date;
    private int capacity;

    public Concert(int concertId, int locationId, String concertName, String date){
        this.concertId = concertId;
        this.locationId = locationId;
        this.concertName = concertName;
        this.date = date;
    }

    public Concert(int concertId, int locationId, String concertName, String date, int capacity){
        this.concertId = concertId;
        this.locationId = locationId;
        this.concertName = concertName;
        this.date = date;
        this.capacity =  capacity;
    }

    public int getConcertId() {return concertId; }
    public int getLocationId() {return locationId; }
    public String getConcertName() {return concertName; }
    public String getDate() {return date; }
    public int getCapacity() {return capacity; }

    public void setLocationId(int locationId) { this.locationId = locationId; }
    public void setConcertName(String concertName) {this.concertName = concertName; }
    public void setDate(String date) { this.date = date; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return "Concert{" +
                "concertId=" + concertId +
                ", locationId=" + locationId +
                ", concertName='" + concertName + '\'' +
                ", date='" + date + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
