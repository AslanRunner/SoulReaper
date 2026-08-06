/**
 * Thrown when the player tries to buy an item without enough coins.
 */
public class InsufficientBudgetException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     *
     * @param message the explanation of the budget error
     */
    public InsufficientBudgetException(String message) {
        super(message);
    }
}
