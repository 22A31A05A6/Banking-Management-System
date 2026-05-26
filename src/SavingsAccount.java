/**
 * SavingsAccount - extends Account
 * Demonstrates:
 * - Inheritance: extends Account
 * - Polymorphism: overrides calculateInterest()
 * - Method Overriding: getAccountType()
 */
public class SavingsAccount extends Account {

    private static final double INTEREST_RATE = 4.0; // 4% per annum
    private static final double MIN_BALANCE = 500.0;  // minimum balance required

    public SavingsAccount(String accountNumber, String accountHolder, double initialDeposit)
            throws InvalidAmountException {
        super(accountNumber, accountHolder, initialDeposit);
        if (initialDeposit < MIN_BALANCE) {
            throw new InvalidAmountException(
                "Savings account requires minimum deposit of ₹" + MIN_BALANCE);
        }
    }

    // Overriding abstract method - Polymorphism
    @Override
    public double calculateInterest() {
        return (getBalance() * INTEREST_RATE) / 100;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    // Override withdraw to enforce minimum balance
    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (getBalance() - amount < MIN_BALANCE) {
            throw new InsufficientFundsException(
                "Cannot withdraw! Minimum balance of ₹" + MIN_BALANCE + " must be maintained.");
        }
        super.withdraw(amount);
    }

    public static double getInterestRate() { return INTEREST_RATE; }
    public static double getMinBalance()   { return MIN_BALANCE; }
}
