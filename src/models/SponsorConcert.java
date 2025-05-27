package models;

public class SponsorConcert {
    private final int concertId;
    private final int sponsorId;
    private SponsorType sponsorType; //si aici Enum

    public SponsorConcert(int concertId, int sponsorId, SponsorType sponsorType) {
        this.concertId = concertId;
        this.sponsorId = sponsorId;
        this.sponsorType = sponsorType;
    }

    public int getConcertId() {return concertId;}
    public int getSponsorId() {return sponsorId;}
    public SponsorType getSponsorType() {return sponsorType;}

    public void setSponsorType(SponsorType sponsorType) {this.sponsorType = sponsorType;}

    @Override
    public String toString() {
        return "SponsorConcert{" +
                "concertId=" + concertId +
                ", sponsorId=" + sponsorId +
                ", sponsorType='" + sponsorType + '\'' +
                '}';
    }
}
