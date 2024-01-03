package hr.javafx.model.remenar7.exception;

/**
 * Predstavla iznimku koja se baca ukoliko se u glavnom programu unese već postojeća kategorija
 */
public class ExistingCategoryException extends RuntimeException{

    public ExistingCategoryException(String message) {
        super(message);
    }

    public ExistingCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingCategoryException(Throwable cause) {
        super(cause);
    }
}
