package db_methods;
import java.sql.*;
import utils.Database;
import models.Country;
import java.util.ArrayList;
import java.util.List;


public class CountryDbMethods {

    public void insertCountry(Country country){
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String insertCountry = "Insert into country(country_name, population) values(?, ?)";

            try(PreparedStatement stmt = conn.prepareStatement(insertCountry)){
                stmt.setString(1, country.getCountry_name());
                stmt.setLong(2, country.getPopulation());
                stmt.executeUpdate();
            }

            conn.commit();

        }  catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void updateCountry(Country country){
        try(Connection conn = Database.getConnection()){
            conn.setAutoCommit(false);
            final String updateCountry = "Update country set country_name=?, population=? where id_country=?";

            try(PreparedStatement stmt = conn.prepareStatement(updateCountry)){
                stmt.setString(1, country.getCountry_name());
                stmt.setLong(2, country.getPopulation());
                stmt.setInt(3, country.getCountryId());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void deleteCountry(int id){
        try(Connection conn = Database.getConnection()){
            try(Statement pragmaStmt = conn.createStatement()){
                pragmaStmt.execute("pragma foreign_keys = ON");
            }
            conn.setAutoCommit(false);
            final String deleteCountry = "Delete from country where id_country=?";

            try(PreparedStatement stmt = conn.prepareStatement(deleteCountry)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            conn.commit();

        }  catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public List<Country> selectAll(){
        List<Country> countries = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            //conn.setAutoCommit(false); //nu dau commit la nimic oricum
            final String retrieveCountries = "Select * from country";

            try(PreparedStatement stmt = conn.prepareStatement(retrieveCountries)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_country");
                    String country_name = rs.getString("country_name");
                    long population = rs.getLong("population");
                    countries.add(new Country(id,country_name,population));
                }
            }

        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }

        return countries;
    }

    public Country selectCountryById(int id){
        try(Connection conn = Database.getConnection()){
            final String getCountry= "Select * from country where id_country = ?";

            try(PreparedStatement stmt = conn.prepareStatement(getCountry)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int country_id = rs.getInt("id_country");
                    String country_name = rs.getString("country_name");
                    long population = rs.getLong("population");
                    return new Country(country_id, country_name, population);
                }
            }


        }  catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }

        return null;
    }
}
