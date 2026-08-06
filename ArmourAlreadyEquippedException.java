/**
 * Thrown when the player tries to equip armour that is already active.
 */
public class ArmourAlreadyEquippedException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the armour equip error
     */
    public ArmourAlreadyEquippedException(String message) {
        super(message);
    }
}
