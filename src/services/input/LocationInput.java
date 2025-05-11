package services.input;
import models.Location;
import java.util.Scanner;

public class LocationInput {
    public static Location createLocationInput(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Choose Country Id: ");
        int countryId=0;
        while(true){
            try{
                countryId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e){
                System.out.print("Enter valid id: ");
            }
        }

        System.out.println("Enter City Name: ");
        String cityName = sc.nextLine();
        System.out.println("Enter Address: ");
        String address = sc.nextLine();

        return new Location(countryId,cityName,address);
    }
}
