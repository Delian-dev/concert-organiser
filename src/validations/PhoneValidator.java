package validations;

public final class PhoneValidator implements Validator<String> {
    @Override
    public boolean isValid(String phone) {
        return phone!=null && phone.matches("^07\\d{8}$");
    }
}
