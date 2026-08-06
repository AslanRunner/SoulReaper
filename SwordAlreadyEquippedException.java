/**
 * Thrown when the hero tries to equip a weapon that is already active.
 */
public class SwordAlreadyEquippedException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the sword equip error
     */
    public SwordAlreadyEquippedException(String message) {
        super(message);
    }
}
