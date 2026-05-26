/**
 * Custom Exception - InsufficientFundsException
 * Demonstrates: Custom Exception Handling
 * Thrown when withdrawal exceeds available balance
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
