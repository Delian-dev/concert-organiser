package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.SoloArtist;
import utils.Database;

public class SoloArtistDbMethods {
    private static final SoloArtistDbMethods instance = new SoloArtistDbMethods();
    private SoloArtistDbMethods() {}

    public static SoloArtistDbMethods getInstance() {
        return instance;
    }

    public void insertSoloArtist(SoloArtist soloArtist) throws SQLException {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }
            conn.setAutoCommit(false);
            final String insertSoloArtist = "insert into solo_artist(id_musician, birthdate, instrument) values (?,?,?)";
            final String insertMusician = "insert into musician(name, genre) values (?, ?)";

            int generatedMusicianId = -1;
            //inserare in tabela muzician
            try(PreparedStatement stmt = conn.prepareStatement(insertMusician)){
                stmt.setString(1, soloArtist.getName());
                stmt.setString(2, soloArtist.getGenre());
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedMusicianId = generatedKeys.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("Inserting musician failed, no ID obtained.");
                    }
                }
            }
            conn.commit();

            //inserare in tabela artistsolo
            try(PreparedStatement stmt = conn.prepareStatement(insertSoloArtist)){
                stmt.setInt(1, generatedMusicianId);
                stmt.setString(2, soloArtist.getBirthdate());
                stmt.setString(3, soloArtist.getInstrument());
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }

    public void updateSoloArtist(SoloArtist soloArtist)  throws SQLException {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }
            conn.setAutoCommit(false);
            final String updateMusician = "update musician set name=?, genre=? where id_musician=?";
            final String updateSoloArtist = "update solo_artist set birthdate=?, instrument=? where id_musician=?";

            //update tabela muzician
            try(PreparedStatement stmt = conn.prepareStatement(updateMusician)){
                stmt.setString(1, soloArtist.getName());
                stmt.setString(2, soloArtist.getGenre());
                stmt.setInt(3, soloArtist.getMusicianId());
                stmt.executeUpdate();
            }
            conn.commit();

            //update table soloartist
            try(PreparedStatement stmt = conn.prepareStatement(updateSoloArtist)){
                stmt.setString(1, soloArtist.getBirthdate());
                stmt.setString(2, soloArtist.getInstrument());
                stmt.setInt(3, soloArtist.getMusicianId());
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }

    public void deleteSoloArtist(int id) throws SQLException {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }

            conn.setAutoCommit(false);
            final String deleteSoloArtist = "delete from solo_artist where id_musician=?";
            final String deleteMusician = "delete from musician where id_musician=?";

            //first delete from soloArtist in order to avoid FK error
            try(PreparedStatement stmt = conn.prepareStatement(deleteSoloArtist)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();

            try(PreparedStatement stmt = conn.prepareStatement(deleteMusician)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }

    public List<SoloArtist> selectAll() {
        List<SoloArtist> soloArtists = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = """
                    SELECT m.id_musician, m.name, m.genre, sa.birthdate, sa.instrument
                    FROM solo_artist sa
                    JOIN musician m ON sa.id_musician = m.id_musician
                """;

            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int musicianId = rs.getInt("id_musician");
                    String musicianName = rs.getString("name");
                    String genre = rs.getString("genre");
                    String birthdate = rs.getString("birthdate");
                    String instrument = rs.getString("instrument");
                    soloArtists.add(new SoloArtist(musicianId, musicianName, genre, birthdate, instrument));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return soloArtists;
    }

    public SoloArtist selectSoloArtistById(int id) throws SQLException{
        try(Connection conn = Database.getConnection()){
            final String selectArtist = "select * from solo_artist where id_musician=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectArtist)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int musicianId = rs.getInt("id_musician");
                    String musicianName = rs.getString("name");
                    String genre = rs.getString("genre");
                    String birthdate = rs.getString("birthdate");
                    String instrument = rs.getString("instrument");
                    return new SoloArtist(musicianId, musicianName, genre, birthdate, instrument);
                }
            }

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

//    public List<SoloArtist> selectSoloArtistByConcertId(int concert_id) {
//        List<SoloArtist> soloArtists = new ArrayList<>();
//        try(Connection conn = Database.getConnection()){
//            final String selectArtist = "select * from solo_artist sa join main.musician m on m.id_musician = sa.id_musician where m.=?";
//            try(PreparedStatement stmt = conn.prepareStatement(selectArtist)){
//                stmt.setInt(1, concert_id);
//            }
//        } catch (SQLException ex){
//            System.out.println("Error: " + ex.getMessage());
//        }
//        return soloArtists;
//    }
}
