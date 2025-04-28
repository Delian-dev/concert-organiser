package models;

public class SponsorConcert {
    private final int concertId;
    private final int sponsorId;
    private String sponsorType; //si aici Enum
    
    public SponsorConcert(int concertId, int sponsorId) {
        this.concertId = concertId;
        this.sponsorId = sponsorId;
    }
    
    public SponsorConcert(int concertId, int sponsorId, String sponsorType) {
        this.concertId = concertId;
        this.sponsorId = sponsorId;
        this.sponsorType = sponsorType;
    }

    public int getConcertId() {return concertId;}
    public int getSponsorId() {return sponsorId;}
    public String getSponsorType() {return sponsorType;}

    public void setSponsorType(String sponsorType) {this.sponsorType = sponsorType;}

    @Override
    public String toString() {
        return "SponsorConcert{" +
                "concertId=" + concertId +
                ", sponsorId=" + sponsorId +
                ", sponsorType='" + sponsorType + '\'' +
                '}';
    }
}
