package db_methods;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidDateException;
import models.Concert;
import utils.Database;
import validations.Validator;

public class ConcertDbMethods {
    public void insertConcert(Concert concert) throws SQLException {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);
            final String insertConcert = "insert into concert(id_location, concert_name, date, capacity) values(?,?,?,?)";

            try(PreparedStatement stmt = conn.prepareStatement(insertConcert)){
                stmt.setInt(1,concert.getLocationId());
                stmt.setString(2,concert.getConcertName());
                stmt.setString(3,concert.getDate());
                stmt.setInt(4,concert.getCapacity());
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }

    public void updateConcert(Concert concert) throws SQLException {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }
            conn.setAutoCommit(false);
            final String updateConcert = "update concert set id_location=?, concert_name=?, date=?, capacity=? where id_concert = ?";

            try(PreparedStatement stmt = conn.prepareStatement(updateConcert)){
                stmt.setInt(1,concert.getLocationId());
                stmt.setString(2,concert.getConcertName());
                stmt.setString(3,concert.getDate());
                stmt.setInt(4,concert.getCapacity());
                stmt.setInt(5,concert.getConcertId());
                stmt.executeUpdate();
            }
            conn.commit();

        }
    }

    public void deleteConcert(int id) {
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);
            final String deleteConcert = "delete from concert where id_concert = ?";

            try(PreparedStatement stmt = conn.prepareStatement(deleteConcert)){
                stmt.setInt(1,id);
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public List<Concert> selectAll(){
        List<Concert> concerts = new ArrayList<>();
        try(Connection conn = Database.getConnection()) {
            final String selectAll = "select * from concert";
            try(PreparedStatement stmt = conn.prepareStatement(selectAll)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    int id = rs.getInt("id_concert");
                    int locationId = rs.getInt("id_location");
                    String concertName = rs.getString("concert_name");
                    String date = rs.getString("date");
                    int capacity = rs.getInt("capacity");
                    concerts.add(new Concert(id, locationId, concertName, date, capacity));
                }
            }

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return concerts;
    }

    public Concert selectConcertById(int id){
        try(Connection conn = Database.getConnection()){

            final String selectConcert = "select * from concert where id_concert = ?";
            try(PreparedStatement stmt = conn.prepareStatement(selectConcert)){
                stmt.setInt(1,id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    int locationId = rs.getInt("id_location");
                    String concertName = rs.getString("concert_name");
                    String date = rs.getString("date");
                    int capacity = rs.getInt("capacity");
                    return new Concert(id, locationId, concertName, date, capacity);
                }
            }

        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return null;
    }

    public List<Concert> selectConcertsByLocation(int locationId){
        List<Concert> concerts = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectConcertsByLocation = "select * from concert where id_location = ?";
            try(PreparedStatement stmt = conn.prepareStatement(selectConcertsByLocation)){
                stmt.setInt(1,locationId);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    int id = rs.getInt("id_concert");
                    String concertName = rs.getString("concert_name");
                    String date = rs.getString("date");
                    int capacity = rs.getInt("capacity");
                    concerts.add(new Concert(id,locationId, concertName, date, capacity));
                }
            }
        } catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return concerts;
    }
}
