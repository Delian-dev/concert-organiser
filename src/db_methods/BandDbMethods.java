package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Band;
import utils.Database;

public class BandDbMethods {
    private static final BandDbMethods instance = new BandDbMethods();
    private BandDbMethods() {}

    public static BandDbMethods getInstance() {
        return instance;
    }

    public void insertBand(Band band) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            try (Statement pragmaStmt = conn.createStatement()) {
                pragmaStmt.execute("PRAGMA foreign_keys = ON");
            }

            conn.setAutoCommit(false);

            final String insertMusician = "INSERT INTO musician(name, genre) VALUES (?, ?)";
            final String insertBand = "INSERT INTO band(id_musician, date_formed) VALUES(?, ?)";

            int generatedMusicianId = -1;

            // inserting into musician table first
            try (PreparedStatement stmt = conn.prepareStatement(insertMusician, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, band.getName());
                stmt.setString(2, band.getGenre());
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

            // inserting into band then
            try (PreparedStatement stmt = conn.prepareStatement(insertBand)) {
                stmt.setInt(1, generatedMusicianId);
                stmt.setString(2, band.getDateFormed());
                stmt.executeUpdate();
            }

            conn.commit();
        }
    }


    public void updateBand(Band Band) throws SQLException {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }
            conn.setAutoCommit(false);
            final String updateMusician = "update musician set name=?, genre=? where id_musician=?";
            final String updateBand = "update band set date_formed=? where id_musician=?";

            //update tabela muzician
            try(PreparedStatement stmt = conn.prepareStatement(updateMusician)){
                stmt.setString(1, Band.getName());
                stmt.setString(2, Band.getGenre());
                stmt.setInt(3, Band.getMusicianId());
                stmt.executeUpdate();
            }
            conn.commit();

            //update table Band
            try(PreparedStatement stmt = conn.prepareStatement(updateBand)){
                stmt.setString(1, Band.getDateFormed());
                stmt.setInt(2, Band.getMusicianId());
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }

    public void deleteBand(int id) throws SQLException{
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }

            conn.setAutoCommit(false);
            final String deleteBand = "delete from band where id_musician=?";
            final String deleteMusician = "delete from musician where id_musician=?";

            //first delete from Band in order to avoid FK error
            try(PreparedStatement stmt = conn.prepareStatement(deleteBand)){
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

    public List<Band> selectAll() {
        List<Band> Bands = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = """
                    SELECT m.id_musician, m.name, m.genre, b.date_formed
                    FROM band b
                    JOIN musician m ON b.id_musician = m.id_musician
                """;

            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int musicianId = rs.getInt("id_musician");
                    String musicianName = rs.getString("name");
                    String genre = rs.getString("genre");
                    String dateFormed = rs.getString("date_formed");
                    Bands.add(new Band(musicianId, musicianName, genre, dateFormed));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return Bands;
    }

    public Band selectBandById(int id) throws SQLException {
        try(Connection conn = Database.getConnection()){
            final String selectArtist = "select * from band where id_musician=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectArtist)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int musicianId = rs.getInt("id_musician");
                    String musicianName = rs.getString("name");
                    String genre = rs.getString("genre");
                    String dateFormed = rs.getString("date_formed");
                    return new Band(musicianId, musicianName, genre, dateFormed);
                }
            }

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }
}
