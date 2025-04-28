package models;

public class Sponsor {
    private final int sponsorId;
    private String sponsorName;
    private long marketValue;

    public Sponsor(int sponsorId, String sponsorName){
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
    }

    public Sponsor(int sponsorId, String sponsorName, long marketValue){
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
        this.marketValue = marketValue;
    }

    public int getSponsorId() { return sponsorId; }
    public String getSponsorName() { return sponsorName; }
    public long getMarketValue() { return marketValue; }

    void setSponsorName(String sponsorName) { this.sponsorName = sponsorName; }
    void setMarketValue(long marketValue) { this.marketValue = marketValue; }

    @Override
    public String toString() {
        return "Sponsor{" +
                "sponsorName='" + sponsorName + '\'' +
                ", marketValue=" + marketValue +
                '}';
    }
}
