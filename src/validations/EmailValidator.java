package validations;

public final class EmailValidator implements Validator<String> {
    @Override
    public boolean isValid(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}


//stil utilizare in service:
//EmailValidator validator = new EmailValidator();
//if (!validator.isValid(email)) {
//        throw new InvalidEmailException("Invalid email format: " + email);
//}
