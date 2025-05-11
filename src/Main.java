import java.sql.*;
import java.util.ArrayList;

import validations.Validator;
import models.*;
import db_methods.*;
import java.util.ArrayList;
import java.util.List;
import services.input.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        CountryDbMethods dbCountry = new CountryDbMethods();
        LocationDbMethods dbLocation = new LocationDbMethods();

        List<Country> countries;
        countries = dbCountry.selectAll();
        for(Country c: countries){
            System.out.println(c);
        }

        List<Location> locations;
        locations = dbLocation.selectAll();
        for(Location l: locations){
            System.out.println(l);
        }

        Country inputCountry = CountryInput.createCountryInput();
        System.out.println(inputCountry);
        dbCountry.insertCountry(inputCountry);

    }
}
