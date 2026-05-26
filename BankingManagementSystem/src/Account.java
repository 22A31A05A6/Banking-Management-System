import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract Account class - Base class for all account types
 * Demonstrates:
 * - Abstraction: abstract method calculateInterest()
 * - Encapsulation: private fields with getters/setters
 * - Serializable: allows saving to file
 */
public abstract class Account implements Serializable {

    // Private fields - Encapsulation
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private ArrayList<String> transactionHistory;

    // Constructor
    public Account(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account opened with initial deposit: ₹" + initialDeposit);
    }

    // Abstract method - each account type calculates interest differently
    public abstract double calculateInterest();

    public abstract String getAccountType();

    // DEPOSIT
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
        addTransaction("Deposited: ₹" + amount + " | Balance: ₹" + balance);
    }

    // WITHDRAW
    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds! Available balance: ₹" + balance);
        }
        balance -= amount;
        addTransaction("Withdrawn: ₹" + amount + " | Balance: ₹" + balance);
    }

    // TRANSFER
    public void transfer(Account target, double amount)
            throws InvalidAmountException, InsufficientFundsException {
        this.withdraw(amount);
        target.deposit(amount);
        addTransaction("Transferred: ₹" + amount + " to Account: " + target.getAccountNumber());
        target.addTransaction("Received: ₹" + amount + " from Account: " + this.accountNumber);
    }

    // Add transaction to history
    protected void addTransaction(String message) {
        transactionHistory.add(message);
    }

    // Getters
    public String getAccountNumber()              { return accountNumber; }
    public String getAccountHolder()              { return accountHolder; }
    public double getBalance()                    { return balance; }
    public ArrayList<String> getTransactionHistory() { return transactionHistory; }

    // Setters
    public void setAccountHolder(String name)     { this.accountHolder = name; }
    protected void setBalance(double balance)     { this.balance = balance; }

    // toString
    @Override
    public String toString() {
        return String.format("| %-12s | %-20s | %-15s | %-12.2f |",
                accountNumber, accountHolder, getAccountType(), balance);
    }
}
