package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Band;
import utils.Database;

public class BandDbMethods {
    public void insertBand(Band Band) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }
            conn.setAutoCommit(false);
            final String insertBand = "insert into band(id_musician, date_formed) values(?,?)";
            final String insertMusician = "insert into musician(name, genre) values (?, ?)";

            //inserare in tabela muzician
            try(PreparedStatement stmt = conn.prepareStatement(insertMusician)){
                stmt.setString(1, Band.getName());
                stmt.setString(2, Band.getGenre());
                stmt.executeUpdate();
            }
            conn.commit();

            //inserare in tabela band
            try(PreparedStatement stmt = conn.prepareStatement(insertBand)){
                stmt.setInt(1, Band.getMusicianId());
                stmt.setString(2, Band.getDateFormed());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void updateBand(Band Band) {
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

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void deleteBand(int id) {
        try(Connection conn = Database.getConnection()){
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

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public List<Band> selectAll() {
        List<Band> Bands = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = "select * from band";

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

    public Band selectBandById(int id) {
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
