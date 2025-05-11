package services.input;
import models.Country;
import java.util.Scanner;

public class CountryInput {
    public static Country createCountryInput(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter country name: ");
        String name = sc.nextLine();

        System.out.print("Enter population: ");
        long population = 0;
        while(true){
            try{
                population = Long.parseLong(sc.nextLine()); //making sure its a number
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter valid number: ");
            }
        }
        return new Country(name, population);
    }
}
