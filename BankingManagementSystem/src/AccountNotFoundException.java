/**
 * Custom Exception - AccountNotFoundException
 * Thrown when an account number doesn't exist in the system
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
