/**
 * Thrown when a new battle is started while another encounter is already active.
 */
public class CombatAlreadyActiveException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the active combat error
     */
    public CombatAlreadyActiveException(String message) {
        super(message);
    }
}
