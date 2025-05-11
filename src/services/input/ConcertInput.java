package services.input;
import models.Concert;
import exceptions.InvalidDateException;
import validations.DateValidator;
import java.util.Scanner;


public class ConcertInput {
    public static Concert createConcertInput(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Location Id: ");
        int locationId=0;
        while(true){
            try{
                locationId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter valid id: ");
            }
        }

        System.out.println("Entere Concert Name: ");
        String concertName = sc.nextLine();

        DateValidator dateValidator = new DateValidator();
        String concertDate="";
        while (true) {
            try {
                System.out.println("Concert Date (YYYY-MM-DD): ");
                concertDate = sc.nextLine();

                if (!dateValidator.isValid(concertDate)) {
                    throw new InvalidDateException("Invalid date format: " + concertDate + ". Use 'YYYY-MM-DD'.");
                }

                break;

            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Enter Concert Capacity: ");
        int capacity = 0;
        while (true) {
            try{
                capacity = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter valid number: ");
            }
        }

        return new Concert(locationId, concertName, concertDate, capacity);
    }
}
