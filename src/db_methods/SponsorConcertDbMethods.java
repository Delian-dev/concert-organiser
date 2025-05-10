package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.SponsorConcert;
import models.SponsorType;
import utils.Database;

public class SponsorConcertDbMethods {
    public void insertSponsorConcert(SponsorConcert SponsorConcert) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = on ");
            }

            conn.setAutoCommit(false);
            final String insertSponsorConcert = "insert into Sponsor_concert(id_concert, id_sponsor, sponsor_type) values (?, ?, ?)";
            try(PreparedStatement stmt = conn.prepareStatement(insertSponsorConcert)){
                stmt.setInt(1, SponsorConcert.getConcertId());
                stmt.setInt(2, SponsorConcert.getSponsorId());
                stmt.setString(3, SponsorConcert.getSponsorType().name());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void updateSponsorConcert(SponsorConcert SponsorConcert) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = on ");
            }

            conn.setAutoCommit(false);
            final String updateSponsorConcert="update sponsor_concert set sponsor_type=? where id_concert=? and id_sponsor=?";
            try(PreparedStatement stmt = conn.prepareStatement(updateSponsorConcert)){
                stmt.setString(1, SponsorConcert.getSponsorType().name());
                stmt.setInt(2, SponsorConcert.getConcertId());
                stmt.setInt(3, SponsorConcert.getSponsorId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void deleteSponsorConcert(int concertId, int sponsorId) {
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String deleteSponsorConcert = "delete from sponsor_concert where id_concert=? and id_sponsor=?";

            try(PreparedStatement stmt = conn.prepareStatement(deleteSponsorConcert)){
                stmt.setInt(1, concertId);
                stmt.setInt(2, sponsorId);
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
