package services.input;
import models.Band;
import exceptions.InvalidDateException;
import validations.DateValidator;
import java.util.Scanner;

public class BandInput {
    public static Band createBandInput(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Band Name: ");
        String name = sc.nextLine();
        System.out.println("Genre: ");
        String genre = sc.nextLine();

        DateValidator dateValidator = new DateValidator();
        String dateFormed="";
        while (true) {
            try {
                System.out.println("Formed Date: (YYYY-MM-DD): ");
                dateFormed = sc.nextLine();

                if (!dateValidator.isValid(dateFormed)) {
                    throw new InvalidDateException("Invalid date format: " + dateFormed + ". Use 'YYYY-MM-DD'.");
                }

                break;

            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
            }
        }

        return new Band(name,genre,dateFormed);
    }
}
