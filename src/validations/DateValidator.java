package validations;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class DateValidator implements Validator<String> {
    @Override
    public boolean isValid(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // ISO format (yyyy-MM-dd) - exactly what I need
            int parsedYear = parsedDate.getYear();
            return parsedYear>=1900 && parsedYear<=2030; //true only if the year is "normal"
        } catch (DateTimeParseException e) { //we want to return true/false so we catch the eventual error caused by .parse
            return false;
        }
    }
}
