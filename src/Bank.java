import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Bank class - Core management layer
 * Demonstrates:
 * - Collections: HashMap for fast account lookup
 * - File Handling: Save/load accounts using Serialization
 * - Stream API: filter, sort, map operations
 * - Polymorphism: treats SavingsAccount and CurrentAccount as Account
 */
public class Bank implements Serializable {

    private String bankName;
    // HashMap - key: accountNumber, value: Account object
    // Why HashMap? O(1) lookup time — faster than ArrayList for search
    private HashMap<String, Account> accounts;
    private int accountCounter;
    private static final String FILE_NAME = "bank_data.dat";

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new HashMap<>();
        this.accountCounter = 1000;
        loadFromFile();
    }

    // Generate unique account number
    private String generateAccountNumber() {
        return "ACC" + (++accountCounter);
    }

    // CREATE Savings Account
    public Account createSavingsAccount(String holderName, double initialDeposit)
            throws InvalidAmountException {
        String accNo = generateAccountNumber();
        SavingsAccount account = new SavingsAccount(accNo, holderName, initialDeposit);
        accounts.put(accNo, account); // HashMap put
        saveToFile();
        return account;
    }

    // CREATE Current Account
    public Account createCurrentAccount(String holderName, double initialDeposit)
            throws InvalidAmountException {
        String accNo = generateAccountNumber();
        CurrentAccount account = new CurrentAccount(accNo, holderName, initialDeposit);
        accounts.put(accNo, account);
        saveToFile();
        return account;
    }

    // GET account by number
    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber); // HashMap get - O(1)
        if (account == null) {
            throw new AccountNotFoundException("Account " + accountNumber + " not found.");
        }
        return account;
    }

    // DEPOSIT
    public void deposit(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        saveToFile();
    }

    // WITHDRAW
    public void withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
        saveToFile();
    }

    // TRANSFER
    public void transfer(String fromAccNo, String toAccNo, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {
        Account from = getAccount(fromAccNo);
        Account to   = getAccount(toAccNo);
        from.transfer(to, amount);
        saveToFile();
    }

    // CLOSE account
    public void closeAccount(String accountNumber) throws AccountNotFoundException {
        if (!accounts.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account " + accountNumber + " not found.");
        }
        accounts.remove(accountNumber); // HashMap remove
        saveToFile();
    }

    // GET all accounts - returns Collection values
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    // SEARCH by holder name - Stream API filter
    public List<Account> searchByName(String name) {
        return accounts.values().stream()           // Stream API
                .filter(a -> a.getAccountHolder()   // filter()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // GET accounts sorted by balance - Stream API sorted
    public List<Account> getAccountsSortedByBalance() {
        return accounts.values().stream()
                .sorted((a, b) -> Double.compare(b.getBalance(), a.getBalance())) // sorted()
                .collect(Collectors.toList());
    }

    // GET total deposits - Stream API reduce/mapToDouble
    public double getTotalDeposits() {
        return accounts.values().stream()
                .mapToDouble(Account::getBalance)   // map()
                .sum();                              // reduce equivalent
    }

    // GET accounts with low balance - Stream API filter
    public List<Account> getLowBalanceAccounts(double threshold) {
        return accounts.values().stream()
                .filter(a -> a.getBalance() < threshold)
                .collect(Collectors.toList());
    }

    // APPLY interest to all accounts - Polymorphism in action
    public void applyInterestToAll() {
        accounts.values().forEach(account -> {
            double interest = account.calculateInterest(); // Polymorphism
            try {
                account.deposit(interest);
            } catch (InvalidAmountException e) {
                System.out.println("Could not apply interest to " + account.getAccountNumber());
            }
        });
        saveToFile();
        System.out.println("✅ Interest applied to all accounts!");
    }

    public int getTotalAccounts() { return accounts.size(); }
    public String getBankName()   { return bankName; }

    // FILE HANDLING - Save
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("[Warning] Could not save data: " + e.getMessage());
        }
    }

    // FILE HANDLING - Load
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Bank saved = (Bank) ois.readObject();
            this.accounts       = saved.accounts;
            this.accountCounter = saved.accountCounter;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Info] Starting fresh — no saved data found.");
        }
    }
}
