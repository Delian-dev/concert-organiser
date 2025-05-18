import java.sql.*;
import java.util.ArrayList;

import gui.MainFrame;
import validations.Validator;
import models.*;
import db_methods.*;
import java.util.ArrayList;
import java.util.List;
import services.input.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        SwingUtilities.invokeLater(MainFrame::new);
//        CountryDbMethods dbCountry = new CountryDbMethods();
//        LocationDbMethods dbLocation = new LocationDbMethods();
//
//        List<Country> countries;
//        countries = dbCountry.selectAll();
//        for(Country c: countries){
//            System.out.println(c);
//        }
//
//        List<Location> locations;
//        locations = dbLocation.selectAll();
//        for(Location l: locations){
//            System.out.println(l);
//        }
//
//        Country inputCountry = CountryInput.createCountryInput();
//        System.out.println(inputCountry);
//        dbCountry.insertCountry(inputCountry);

    }
}
