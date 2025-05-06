package db_methods;
import java.sql.*;
import utils.Database;
import models.Location;
import java.util.ArrayList;
import java.util.List;

public class LocationDbMethods {

    public void insertLocation(Location location) {
        try(Connection conn = Database.getConnection()){
            // Enable foreign keys
            try (Statement pragmaStmt = conn.createStatement()) {
                pragmaStmt.execute("PRAGMA foreign_keys = ON");
            }


            conn.setAutoCommit(false);
            final String insertLocation="Insert into location(id_country, city, address) values(?,?,?)";

            try(PreparedStatement stmt = conn.prepareStatement(insertLocation)){
                stmt.setInt(1, location.getCountryId());
                stmt.setString(2, location.getCity());
                stmt.setString(3, location.getAddress());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void updateLocation(Location location) {
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String updateLocation="Update location set id_country = ?, city = ?, address = ? where id_location = ?";

            try(PreparedStatement stmt = conn.prepareStatement(updateLocation)){
                stmt.setInt(1, location.getCountryId());
                stmt.setString(2, location.getCity());
                stmt.setString(3, location.getAddress());
                stmt.setInt(4, location.getLocationId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void deleteLocation(int id){
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String deleteLocation = "Delete from location where id_location=?";

            try(PreparedStatement stmt = conn.prepareStatement(deleteLocation)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();

        }  catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public List<Location> selectAll(){
        List<Location> locations = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            final String selectLocation = "Select * from location";
            try(PreparedStatement stmt = conn.prepareStatement(selectLocation)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int locationId = rs.getInt("id_location");
                    int countryId = rs.getInt("id_country");
                    String city = rs.getString("city");
                    String address = rs.getString("address");
                    locations.add(new Location(locationId, countryId, city, address));
                }
            }


        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return locations;
    }

    public Location selectLocationById(int id){
        try(Connection conn = Database.getConnection()){
            final String getLocation = "Select * from location where id_location=?";
            try(PreparedStatement stmt = conn.prepareStatement(getLocation)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int locationId = rs.getInt("id_location");
                    int countryId = rs.getInt("id_country");
                    String city = rs.getString("city");
                    String address = rs.getString("address");
                    return new Location(locationId, countryId, city, address);
                }
            }

        } catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }
}
