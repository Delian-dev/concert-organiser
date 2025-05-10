package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Sponsor;
import utils.Database;

public class SponsorDbMethods {
    void insertSponsor(Sponsor sponsor) {
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String insertSponsor="insert into sponsor(sponsor_name, market_value) values(?,?)";
            try(PreparedStatement stmt = conn.prepareStatement(insertSponsor)){
                stmt.setString(1, sponsor.getSponsorName());
                stmt.setLong(2, sponsor.getMarketValue());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    void updateSponsor(Sponsor sponsor) {
        try(Connection conn =  Database.getConnection()){
            conn.setAutoCommit(false);
            final String updateSponsor = "update sponsor set sponsor_name=?, market_value=? where id_sponsor=?";

            try(PreparedStatement stmt = conn.prepareStatement(updateSponsor)){
                stmt.setString(1, sponsor.getSponsorName());
                stmt.setLong(2, sponsor.getMarketValue());
                stmt.setInt(3, sponsor.getSponsorId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    void deleteSponsor(int id){
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String deleteSponsor = "delete from sponsor where id_sponsor=?";
            try(PreparedStatement stmt = conn.prepareStatement(deleteSponsor)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public List<Sponsor> selectAll(){
        List<Sponsor> sponsors = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectAll = "select * from sponsor";
            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_sponsor");
                    String sponsorName = rs.getString("sponsor_name");
                    long marketValue = rs.getLong("market_value");
                    sponsors.add(new Sponsor(id,sponsorName,marketValue));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return sponsors;
    }

    public Sponsor selectSponsorById(int id){
        try(Connection conn = Database.getConnection()){
            final String selectSponsor = "select * from sponsor where id_sponsor=?";
            try(PreparedStatement stmt = conn.prepareStatement(selectSponsor)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int sponsorId = rs.getInt("id_sponsor");
                    String sponsorName = rs.getString("sponsor_name");
                    long marketValue = rs.getLong("market_value");
                    return new Sponsor(sponsorId,sponsorName,marketValue);
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return null;
    }
}
