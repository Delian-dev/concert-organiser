package models;

public class Musician {
    private int musicianId;
    private String name;
    private String genre;

    public Musician(int musicianId, String name, String genre){
        this.musicianId = musicianId;
        this.name = name;
        this.genre = genre;
    }

    public Musician(String name, String genre){
        this.name = name;
        this.genre = genre;
    }


    public int getMusicianId(){ return musicianId; }
    public String getName(){ return name; }
    public String getGenre(){ return genre; }

    public void setName(String name){this.name = name;}
    public void setGenre(String genre){this.genre = genre;}

    @Override
    public String toString() {
        return name;
    }
}
