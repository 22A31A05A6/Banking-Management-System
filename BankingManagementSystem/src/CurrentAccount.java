/**
 * CurrentAccount - extends Account
 * Demonstrates:
 * - Inheritance: extends Account
 * - Polymorphism: overrides calculateInterest() and withdraw()
 * - Overdraft facility: unique to CurrentAccount
 */
public class CurrentAccount extends Account {

    private static final double INTEREST_RATE = 2.0;    // 2% per annum
    private static final double OVERDRAFT_LIMIT = 10000; // can go negative up to this

    public CurrentAccount(String accountNumber, String accountHolder, double initialDeposit)
            throws InvalidAmountException {
        super(accountNumber, accountHolder, initialDeposit);
        if (initialDeposit < 0) {
            throw new InvalidAmountException("Initial deposit cannot be negative.");
        }
    }

    // Overriding abstract method - Polymorphism
    @Override
    public double calculateInterest() {
        return (getBalance() * INTEREST_RATE) / 100;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    // Override withdraw - allows overdraft up to OVERDRAFT_LIMIT
    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException(
                "Overdraft limit of ₹" + OVERDRAFT_LIMIT + " exceeded!");
        }
        setBalance(getBalance() - amount);
        addTransaction("Withdrawn: ₹" + amount + " | Balance: ₹" + getBalance());
    }

    public static double getOverdraftLimit() { return OVERDRAFT_LIMIT; }
    public static double getInterestRate()   { return INTEREST_RATE; }
}
