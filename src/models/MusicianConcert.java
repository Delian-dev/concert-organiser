package models;

public class MusicianConcert {
    private final int concertId;
    private final int musicianId;
    private int musicianFee;
    private int performanceDuration;

    public MusicianConcert(int concertId,int musicianId, int musicianFee, int performanceDuration) {
        this.musicianId = musicianId;
        this.concertId = concertId;
        this.musicianFee = musicianFee;
        this.performanceDuration = performanceDuration;
    }

    public int getMusicianId() { return musicianId; }
    public int getConcertId() { return concertId; }
    public int getMusicianFee() { return musicianFee; }
    public int getPerformanceDuration() { return performanceDuration; }

    public void setMusicianFee(int musicianFee) { this.musicianFee = musicianFee; }
    public void setPerformanceDuration(int performanceDuration) { this.performanceDuration = performanceDuration; }

    @Override
    public String toString() {
        return "MusicianConcert{" +
                "musicianId=" + musicianId + //probabil o sa modific aici ca sa am nume in loc de id-uri
                ", concertId=" + concertId +
                ", musicianFee=" + musicianFee +
                ", performanceDuration=" + performanceDuration +
                '}';
    }
}
