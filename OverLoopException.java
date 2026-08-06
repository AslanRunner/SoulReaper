/**
 * Thrown when the player exceeds the maximum allowed number of playthroughs.
 */
public class OverLoopException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the playthrough limit error
     */
    public OverLoopException(String message) {
        super(message);
    }
}
