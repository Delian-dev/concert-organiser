package models;

public class SoloArtist extends Musician {
    private String birthdate;
    private String instrument;

    public SoloArtist(int musicianId, String name, String genre, String birthdate, String instrument){
        super(musicianId, name, genre);
        this.birthdate = birthdate;
        this.instrument = instrument;
    }

    public SoloArtist(String name, String genre, String birthdate, String instrument){
        super(name, genre);
        this.birthdate = birthdate;
        this.instrument = instrument;
    }

    public String getBirthdate(){ return birthdate; }
    public String getInstrument(){ return instrument; }

    public void setBirthdate(String birthdate){ this.birthdate = birthdate; }
    public void setInstrument(String instrument){ this.instrument = instrument; }

    @Override
    public String toString() {
        return "SoloArtist{" +
                "name='" + getName() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", instrument='" + instrument + '\'' +
                '}';
    }
}
