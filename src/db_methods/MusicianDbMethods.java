package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Concert;
import models.Musician;
import models.SoloArtist;
import models.Band;
import utils.Database;

public class MusicianDbMethods {
    private static final MusicianDbMethods instance = new MusicianDbMethods();
    private MusicianDbMethods() {}

    public static MusicianDbMethods getInstance() {
        return instance;
    }

    public List<Musician> selectAll() {
        List<Musician> musicians = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = """
                    SELECT * from musician m;
                """;

            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int musicianId = rs.getInt("id_musician");
                    String musicianName = rs.getString("name");
                    String genre = rs.getString("genre");
                    musicians.add(new Musician(musicianId, musicianName, genre));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return musicians;
    }

    public List<Musician> getMusiciansByConcert(int concertId) {
        List<Musician> musicians = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectMusicianByConcert = "SELECT" +
                    " m.id_musician, m.name, m.genre, sa.birthdate, sa.instrument, b.date_formed" +
                    "            FROM musician m" +
                    "            JOIN musician_concert mc ON m.id_musician = mc.id_musician" +
                    "            LEFT JOIN solo_artist sa ON m.id_musician = sa.id_musician" +
                    "            LEFT JOIN band b ON m.id_musician = b.id_musician" +
                    "            WHERE mc.id_concert = ?;";
            try(PreparedStatement stmt = conn.prepareStatement(selectMusicianByConcert)){
                stmt.setInt(1, concertId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_musician");
                    String name = rs.getString("name");
                    String genre = rs.getString("genre");

                    String birthdate = rs.getString("birthdate");
                    String instrument = rs.getString("instrument");
                    String dateFormed = rs.getString("date_formed");

                    if (birthdate != null) { //it's a solo artist
                        musicians.add(new SoloArtist(id, name, genre, birthdate, instrument));
                    }
                    else{
                        musicians.add(new Band(id,name,genre,dateFormed));
                    }
                }
            }

        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }

        return musicians;
    }

    public List<Concert> selectConcertsByMusicianId(int musicianId){
        List<Concert> concerts = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectConcerts = """
                         SELECT c.id_concert, c.concert_name, c.date, c.id_location, c.capacity
                         FROM concert c
                         INNER JOIN musician_concert mc ON c.id_concert = mc.id_concert
                         WHERE mc.id_musician = ?
                    """;
            try(PreparedStatement stmt = conn.prepareStatement(selectConcerts)){
                stmt.setInt(1, musicianId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int concertId = rs.getInt("id_concert");
                    String concertName = rs.getString("concert_name");
                    String date = rs.getString("date");
                    int locationId = rs.getInt("id_location");
                    int capacity = rs.getInt("capacity");
                    concerts.add(new Concert(concertId,locationId,concertName,date,capacity));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }

        return concerts;
    }
}
