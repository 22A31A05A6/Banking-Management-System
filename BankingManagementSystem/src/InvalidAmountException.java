/**
 * Custom Exception - InvalidAmountException
 * Demonstrates: Custom Exception Handling
 * Thrown when an invalid amount is entered
 */
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
