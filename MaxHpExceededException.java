/**
 * Thrown when healing would exceed the target's maximum HP.
 */
public class MaxHpExceededException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the maximum HP error
     */
    public MaxHpExceededException(String message) {
        super(message);
    }
}
