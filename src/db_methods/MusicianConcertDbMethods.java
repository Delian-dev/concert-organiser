package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.MusicianConcert;
import utils.Database;

public class MusicianConcertDbMethods {
    public void insertMusicianConcert(MusicianConcert musicianConcert) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = on ");
            }

            conn.setAutoCommit(false);
            final String insertMusicianConcert = "insert into musician_concert(id_concert, id_musician, musician_fee, performance_duration) values (?, ?, ?, ?)";
            try(PreparedStatement stmt = conn.prepareStatement(insertMusicianConcert)){
                stmt.setInt(1, musicianConcert.getConcertId());
                stmt.setInt(2, musicianConcert.getMusicianId());
                stmt.setInt(3, musicianConcert.getMusicianFee());
                stmt.setInt(4, musicianConcert.getPerformanceDuration());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void updateMusicianConcert(MusicianConcert musicianConcert) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = on ");
            }

            conn.setAutoCommit(false);
            final String updateMusicianConcert="update musician_concert set musician_fee=?, performance_duration=? where id_concert=? and id_musician=?";
            try(PreparedStatement stmt = conn.prepareStatement(updateMusicianConcert)){
                stmt.setInt(1, musicianConcert.getMusicianFee());
                stmt.setInt(2,musicianConcert.getPerformanceDuration());
                stmt.setInt(3, musicianConcert.getConcertId());
                stmt.setInt(4, musicianConcert.getMusicianId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void deleteMusicianConcert(int concertId, int musicianId) {
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String deleteMusicianConcert = "delete from musician_concert where id_concert=? and id_musician=?";

            try(PreparedStatement stmt = conn.prepareStatement(deleteMusicianConcert)){
                stmt.setInt(1, concertId);
                stmt.setInt(2, musicianId);
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
