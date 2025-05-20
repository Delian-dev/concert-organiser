package services.input;
import models.SoloArtist;
import exceptions.InvalidDateException;
import validations.DateValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class SoloArtistInput {
    public static SoloArtist createSoloArtistInput() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Artist Name: ");
        String name = sc.nextLine();
        System.out.println("Genre: ");
        String genre = sc.nextLine();

        DateValidator dateValidator = new DateValidator();
        String birthdate="";
        while (true) {
            try {
                System.out.println("Artist Birthdate (YYYY-MM-DD): ");
                birthdate = sc.nextLine();

                if (!dateValidator.isValid(birthdate)) {
                    throw new InvalidDateException("Invalid date format: " + birthdate + ". Use 'YYYY-MM-DD'.");
                }

                break;

            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Artist Instrument: ");
        String instrument = sc.nextLine();

        return new SoloArtist(name,genre,birthdate,instrument);
    }
}
