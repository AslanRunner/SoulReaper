import java.util.Scanner;
/**
 * Provides helper methods for safely reading numeric input.
 */
public class InputHelper {
    /**
     * Creates an input helper instance.
     */
    public InputHelper() {
    }
    /**
     * Prompts the user for input and safely parses it as an integer.
     *
     * @param scanner the scanner used to read input
     * @param prompt the text shown before reading input
     * @return the parsed integer value
     */
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.next();

            try {
                return Integer.parseInt(input); // Converts the input string directly to an integer.
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a numeric value.");
            }
        }
    }
    /**
     * Reads an integer and validates that it is inside the given range.
     *
     * @param scanner the scanner used to read input
     * @param prompt the text shown before reading input
     * @param min the minimum accepted value
     * @param max the maximum accepted value
     * @return the accepted integer value
     */
    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);
            try {
                if (value < min || value > max) {
                    throw new InvalidSelectionException("Choice must be between " + min + " and " + max + ".");
                }
                return value;
            } catch (InvalidSelectionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    /**
     * Reads an integer and accepts only one of the two allowed values.
     *
     * @param scanner the scanner used to read input
     * @param prompt the text shown before reading input
     * @param firstAllowed the first accepted value
     * @param secondAllowed the second accepted value
     * @return the accepted integer value
     */
    public static int readAllowedInt(Scanner scanner, String prompt, int firstAllowed, int secondAllowed) {
        while (true) {
            int value = readInt(scanner, prompt);
            try {
                if (value != firstAllowed && value != secondAllowed) {
                    throw new InvalidSelectionException("Invalid choice! Valid values are "
                            + firstAllowed + " or " + secondAllowed + ".");
                }
                return value;
            } catch (InvalidSelectionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
