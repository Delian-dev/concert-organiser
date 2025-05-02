package exceptions;

public final class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) { //then I can throw an InvalidDateException with my own message when it is necessary
        super(message); //my message gets sent to the RuntimeException constructor
    }
}
