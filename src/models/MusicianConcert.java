package models;

public class MusicianConcert {
    private final int musicianId;
    private final int concertId;
    private int musicianFee;
    private int performanceDuration;

    public MusicianConcert(int musicianId, int concertId) {
        this.musicianId = musicianId;
        this.concertId = concertId;
    }

    public MusicianConcert(int musicianId, int concertId, int musicianFee) {
        this.musicianId = musicianId;
        this.concertId = concertId;
        this.musicianFee = musicianFee;
    }

    public MusicianConcert(int musicianId, int concertId, int musicianFee, int performanceDuration) {
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
