package services.input;
import models.Sponsor;
import models.SponsorType;
import java.util.Scanner;

public class SponsorInput {
    public static Sponsor createSponsorInput(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Sponsor Name: ");
        String sponsorName = sc.nextLine();

        System.out.println("Market Value: ");
        long marketValue = 0;
        while(true){
            try {
                marketValue = Integer.parseInt(sc.nextLine());
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Enter a valid number: ");
            }
        }
        return new Sponsor(sponsorName, marketValue);
    }
}
