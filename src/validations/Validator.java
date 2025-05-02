package validations;

public interface Validator<T> {
    public boolean isValid(T input);
}
