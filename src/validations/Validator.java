package validations;

public interface Validator<T> {
    boolean isValid(T input);
}
