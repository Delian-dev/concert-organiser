package validations;

public final class EmailValidator implements Validator<String> {
    @Override
    public boolean isValid(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}
