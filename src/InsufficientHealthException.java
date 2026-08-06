/**
 * Thrown when a character's health would drop below zero.
 */
public class InsufficientHealthException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the health error
     */
    public InsufficientHealthException(String message) {
        super(message);
    }
}
