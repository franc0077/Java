package hr.javafx.model.remenar7.exception;

/**
 * Predstavlja iznimku koja se baca ukoliko se u glavnom programu unese već postojeći artikl
 */
public class ExistingItemException extends Exception{

    public ExistingItemException(String message) {
        super(message);
    }

    public ExistingItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingItemException(Throwable cause) {
        super(cause);
    }
}
