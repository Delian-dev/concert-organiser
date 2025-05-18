package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Musician;
import models.SoloArtist;
import models.Band;
import utils.Database;

public class MusicianDbMethods {
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
}
