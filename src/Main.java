import java.sql.*;
import java.util.ArrayList;

import validations.Validator;
import models.*;
import db_methods.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

//        //testare conexiune db
//        try(Connection conn = utils.Database.getConnection()){
//
//            conn.setAutoCommit(false);
//            final String retrieveTest = "Select * from test";
//            final String insertTest = "Insert into test(field_test) values(?)";
//
//            try(PreparedStatement stmt = conn.prepareStatement(retrieveTest)){
//                ResultSet rs = stmt.executeQuery();
//                while(rs.next()){
//                    System.out.println(rs.getString(1) + " " + rs.getString(2));
//                }
//            }
//
//            try(PreparedStatement stmt = conn.prepareStatement(insertTest)){
//                stmt.setString(1,"john cena");
//                stmt.executeUpdate();
//            }
//
//            conn.commit();
//
//        } catch (SQLException ex){
//            System.out.println("Error: " + ex.getMessage());
//        }
//
//        System.out.println("GG");

          CountryDbMethods dbCountry = new CountryDbMethods();
          LocationDbMethods dbLocation = new LocationDbMethods();

//          Country c1 = new Country("Romania", 18000000);
//          Country c2 = new Country("Bulgaria", 6000000);
//          dbCountry.insertCountry(c1);
//          dbCountry.insertCountry(c2);

          List<Country> countries;
          countries = dbCountry.selectAll();
          for(Country c: countries){
              System.out.println(c);
          }

//          //dbCountry.insertCountry(new Country("Italy", 50000000));
//          Country c_id = dbCountry.selectCountryById(3);
//          System.out.println(c_id);
//
//          //dbCountry.deleteCountry(2);
//
//            Country c2 = countries.get(1);
//            c2.setCountryName("Bulgarini capuccini");
//            dbCountry.updateCountry(c2);

//            Location l1 = new Location(1,"Craiova", "Str. Dr. N. I. Sisesti");
//            Location l2 = new Location(3, "Rome", "Colloseum Entrance A");
            Location l4 = new Location(5, "Sofia", "Str. Dr. N. I. Sisesti");
//            dbLocation.insertLocation(l1);
//            dbLocation.insertLocation(l2);
            dbLocation.insertLocation(l4);
            dbLocation.deleteLocation(4);
            List<Location> locations;
            locations = dbLocation.selectAll();
            for(Location l: locations){
                System.out.println(l);
            }
            Location loc_update = locations.get(2);
            loc_update.setAddress("Modified address");
            dbLocation.updateLocation(loc_update);
    }
}
