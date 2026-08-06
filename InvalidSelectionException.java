/**
 * Thrown when the user enters an invalid menu choice or selects an out-of-range item.
 */
public class InvalidSelectionException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the invalid selection
     */
    public InvalidSelectionException(String message) {
        super(message);
    }
}
