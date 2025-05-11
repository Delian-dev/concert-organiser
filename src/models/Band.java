package models;

public class Band extends Musician{
    private String dateFormed;

    public Band(int musicianId, String name, String genre, String dateFormed){
        super(musicianId, name, genre);
        this.dateFormed = dateFormed;
    }

    public Band(String name, String genre, String dateFormed){
        super(name, genre);
        this.dateFormed = dateFormed;
    }

    public String getDateFormed(){ return dateFormed;}
    public void setDateFormed(String dateFormed){ this.dateFormed = dateFormed;}

    @Override
    public String toString() {
        return "Band{" +
                "name='" + getName() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", dateFormed='" + dateFormed + '\'' +
                '}';
    }
}
