package services.input;
import models.Ticket;
import models.TicketType;
import exceptions.InvalidDateException;
import validations.DateValidator;
import java.util.Scanner;

public class TicketInput {
    public static Ticket createTicketInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose Concert Id: ");
        int concertId=0;
        while(true){
            try{
                concertId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter valid id: ");
            }
        }

        System.out.print("Choose Client Id: ");
        int clientId=0;
        while(true){
            try{
                clientId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter valid id: ");
            }
        }

        System.out.print("Enter Ticket Price: ");
        int price=0;
        while(true){
            try{
                price = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter a valid number: ");
            }
        }

        TicketType ticketType = null;
        while (ticketType == null) {
            System.out.print("Enter Ticket Type (EARLY BIRD, STANDARD, VIP): ");
            String input = sc.nextLine().toUpperCase();

            try {
                ticketType = TicketType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ticket type");
            }
        }


        DateValidator dateValidator = new DateValidator();
        String transactionDate="";
        while (true) {
            try {
                System.out.println("Transaction Date (YYYY-MM-DD): ");
                transactionDate = sc.nextLine();

                if (!dateValidator.isValid(transactionDate)) {
                    throw new InvalidDateException("Invalid date format: " + transactionDate + ". Use 'YYYY-MM-DD'.");
                }

                break;

            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
            }
        }

        return new Ticket(concertId,clientId,price,ticketType,transactionDate);
    }
}
