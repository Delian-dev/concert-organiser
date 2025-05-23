package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Sponsor;
import models.SponsorConcert;
import models.Concert;
import models.SponsorType;
import utils.Database;

public class SponsorDbMethods {
    public void insertSponsor(Sponsor sponsor) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            final String insertSponsor = "insert into sponsor(sponsor_name, market_value) values(?,?)";

            try (PreparedStatement stmt = conn.prepareStatement(insertSponsor)) {
                stmt.setString(1, sponsor.getSponsorName());
                stmt.setLong(2, sponsor.getMarketValue());
                stmt.executeUpdate();
            }

            conn.commit();
        }
    }

    public void updateSponsor(Sponsor sponsor) throws SQLException {
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

        }
    }

    public void deleteSponsor(int id) throws SQLException{
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }

            conn.setAutoCommit(false);
            final String deleteSponsor = "delete from sponsor where id_sponsor=?";
            try(PreparedStatement stmt = conn.prepareStatement(deleteSponsor)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();
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

    public Map<Sponsor, SponsorType> getSponsorsbyConcertId(int concertId){
        Map<Sponsor, SponsorType> sponsorsMap = new LinkedHashMap<>();
        try(Connection conn = Database.getConnection()){
            final String getSponsors = " SELECT" +
                    " s.id_sponsor, s.sponsor_name,s.market_value,sc.sponsor_type" +
                    "            FROM sponsor_concert sc" +
                    "            JOIN sponsor s ON sc.id_sponsor = s.id_sponsor" +
                    "            WHERE sc.id_concert = ?;";
            try(PreparedStatement stmt = conn.prepareStatement(getSponsors)){
                stmt.setInt(1, concertId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_sponsor");
                    String name = rs.getString("sponsor_name");
                    long value = rs.getLong("market_value");
                    Sponsor sponsor = new Sponsor(id, name, value);

                    SponsorType sponsorType = SponsorType.valueOf(rs.getString("sponsor_type").toUpperCase());

                    sponsorsMap.put(sponsor, sponsorType);
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return sponsorsMap;
    }

    public List<Concert> selectConcertsBySponsorId(int sponsorId){
        List<Concert> concerts = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectConcerts = """
                         SELECT c.id_concert, c.concert_name, c.date, c.id_location, c.capacity
                         FROM concert c
                         INNER JOIN sponsor_concert sc ON c.id_concert = sc.id_concert
                         WHERE sc.id_sponsor = ?
                    """;
            try(PreparedStatement stmt = conn.prepareStatement(selectConcerts)){
                stmt.setInt(1, sponsorId);
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
