package services.input;
import models.Client;
import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneException;
import validations.EmailValidator;
import validations.PhoneValidator;
import java.util.Scanner;

public class ClientInput {
    public static Client createClientInput(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Client Name: ");
        String name = scanner.nextLine();

        System.out.println("Client Age: ");
        int age = 0;
        while(true){
            try{
                age = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.println("Enter a valid number: ");
            }
        }

        EmailValidator emailValidator = new EmailValidator();
        String email = "";
        while(true){
            try{
                System.out.println("Email: ");
                email = scanner.nextLine();

                if(!emailValidator.isValid(email)){
                    throw new InvalidEmailException("Invalid email format: " + email);
                }
                break;
            } catch (InvalidEmailException e){
                System.out.println(e.getMessage());
            }
        }

        PhoneValidator phoneValidator = new PhoneValidator();
        String phone = "";
        while(true){
            try{
                System.out.println("Phone Number: ");
                phone = scanner.nextLine();

                if(!phoneValidator.isValid(phone)){
                    throw new InvalidPhoneException("Invalid phone format: " + phone);
                }
                break;
            } catch (InvalidPhoneException e){
                System.out.println(e.getMessage());
            }
        }

        return new Client(name, age, email, phone);
    }
}
