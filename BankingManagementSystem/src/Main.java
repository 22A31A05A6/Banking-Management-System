import java.util.*;

/**
 * Main class - Console UI and entry point
 * Demonstrates:
 * - Menu driven console application
 * - Exception handling in practice
 * - Polymorphism: Account reference holds SavingsAccount/CurrentAccount
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank("Samuel's Bank");

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║        BANKING MANAGEMENT SYSTEM v1.0        ║");
        System.out.println("║     Built with Java | OOP | Collections      ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("Welcome to " + bank.getBankName());

        int choice = -1;
        while (choice != 0) {
            printMenu();
            choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:  createAccount();          break;
                case 2:  viewAccount();            break;
                case 3:  viewAllAccounts();        break;
                case 4:  deposit();                break;
                case 5:  withdraw();               break;
                case 6:  transfer();               break;
                case 7:  viewTransactionHistory(); break;
                case 8:  searchAccount();          break;
                case 9:  viewSortedByBalance();    break;
                case 10: applyInterest();          break;
                case 11: bankStatistics();         break;
                case 12: closeAccount();           break;
                case 0:
                    System.out.println("\n✅ Data saved. Thank you for banking with us. Goodbye!");
                    break;
                default:
                    System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // ─── MENU ─────────────────────────────────────────────
    private static void printMenu() {
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.println("│             MAIN MENU                │");
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│  1.  Create Account                  │");
        System.out.println("│  2.  View Account Details            │");
        System.out.println("│  3.  View All Accounts               │");
        System.out.println("│  4.  Deposit                         │");
        System.out.println("│  5.  Withdraw                        │");
        System.out.println("│  6.  Transfer                        │");
        System.out.println("│  7.  Transaction History             │");
        System.out.println("│  8.  Search by Name                  │");
        System.out.println("│  9.  Accounts Sorted by Balance      │");
        System.out.println("│  10. Apply Interest to All           │");
        System.out.println("│  11. Bank Statistics                 │");
        System.out.println("│  12. Close Account                   │");
        System.out.println("│  0.  Exit                            │");
        System.out.println("└──────────────────────────────────────┘");
    }

    // ─── CREATE ACCOUNT ───────────────────────────────────
    private static void createAccount() {
        System.out.println("\n── Create New Account ──");
        System.out.println("1. Savings Account (4% interest, min balance ₹500)");
        System.out.println("2. Current Account (2% interest, overdraft ₹10,000)");
        int type = getIntInput("Account type: ");
        String name    = getStringInput("Account Holder Name: ");
        double deposit = getDoubleInput("Initial Deposit (₹): ");

        try {
            Account account;
            if (type == 1) {
                account = bank.createSavingsAccount(name, deposit);
            } else if (type == 2) {
                account = bank.createCurrentAccount(name, deposit);
            } else {
                System.out.println("❌ Invalid account type.");
                return;
            }
            System.out.println("✅ Account created successfully!");
            System.out.println("   Account Number : " + account.getAccountNumber());
            System.out.println("   Account Type   : " + account.getAccountType());
            System.out.println("   Account Holder : " + account.getAccountHolder());
            System.out.println("   Balance        : ₹" + account.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── VIEW ACCOUNT ─────────────────────────────────────
    private static void viewAccount() {
        String accNo = getStringInput("\nEnter Account Number: ");
        try {
            Account account = bank.getAccount(accNo);
            System.out.println("\n── Account Details ──");
            System.out.println("Account Number  : " + account.getAccountNumber());
            System.out.println("Account Holder  : " + account.getAccountHolder());
            System.out.println("Account Type    : " + account.getAccountType());
            System.out.printf ("Balance         : ₹%.2f%n", account.getBalance());
            System.out.printf ("Annual Interest : ₹%.2f%n", account.calculateInterest());
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── VIEW ALL ACCOUNTS ────────────────────────────────
    private static void viewAllAccounts() {
        Collection<Account> all = bank.getAllAccounts();
        if (all.isEmpty()) {
            System.out.println("\n⚠️  No accounts found.");
            return;
        }
        System.out.println("\n── All Accounts ── Total: " + bank.getTotalAccounts());
        printTableHeader();
        for (Account a : all) System.out.println(a);
        printTableFooter();
    }

    // ─── DEPOSIT ──────────────────────────────────────────
    private static void deposit() {
        String accNo  = getStringInput("\nEnter Account Number: ");
        double amount = getDoubleInput("Enter Deposit Amount (₹): ");
        try {
            bank.deposit(accNo, amount);
            System.out.println("✅ ₹" + amount + " deposited successfully!");
            System.out.printf ("   New Balance: ₹%.2f%n", bank.getAccount(accNo).getBalance());
        } catch (AccountNotFoundException | InvalidAmountException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── WITHDRAW ─────────────────────────────────────────
    private static void withdraw() {
        String accNo  = getStringInput("\nEnter Account Number: ");
        double amount = getDoubleInput("Enter Withdrawal Amount (₹): ");
        try {
            bank.withdraw(accNo, amount);
            System.out.println("✅ ₹" + amount + " withdrawn successfully!");
            System.out.printf ("   New Balance: ₹%.2f%n", bank.getAccount(accNo).getBalance());
        } catch (AccountNotFoundException | InvalidAmountException | InsufficientFundsException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── TRANSFER ─────────────────────────────────────────
    private static void transfer() {
        System.out.println("\n── Fund Transfer ──");
        String from   = getStringInput("From Account Number: ");
        String to     = getStringInput("To Account Number  : ");
        double amount = getDoubleInput("Transfer Amount (₹): ");
        try {
            bank.transfer(from, to, amount);
            System.out.println("✅ ₹" + amount + " transferred successfully!");
        } catch (AccountNotFoundException | InvalidAmountException | InsufficientFundsException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── TRANSACTION HISTORY ──────────────────────────────
    private static void viewTransactionHistory() {
        String accNo = getStringInput("\nEnter Account Number: ");
        try {
            Account account = bank.getAccount(accNo);
            ArrayList<String> history = account.getTransactionHistory();
            System.out.println("\n── Transaction History: " + accNo + " ──");
            if (history.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                for (int i = 0; i < history.size(); i++) {
                    System.out.println((i + 1) + ". " + history.get(i));
                }
            }
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── SEARCH ───────────────────────────────────────────
    private static void searchAccount() {
        String name = getStringInput("\nEnter Name to Search: ");
        List<Account> results = bank.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("❌ No accounts found for: " + name);
        } else {
            System.out.println("\n── Search Results ──");
            printTableHeader();
            for (Account a : results) System.out.println(a);
            printTableFooter();
        }
    }

    // ─── SORTED BY BALANCE ────────────────────────────────
    private static void viewSortedByBalance() {
        List<Account> sorted = bank.getAccountsSortedByBalance();
        if (sorted.isEmpty()) {
            System.out.println("\n⚠️  No accounts found.");
            return;
        }
        System.out.println("\n── Accounts Sorted by Balance (High to Low) ──");
        printTableHeader();
        for (Account a : sorted) System.out.println(a);
        printTableFooter();
    }

    // ─── APPLY INTEREST ───────────────────────────────────
    private static void applyInterest() {
        System.out.println("\n── Apply Annual Interest ──");
        String confirm = getStringInput("Apply interest to ALL accounts? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            bank.applyInterestToAll();
        } else {
            System.out.println("⚠️  Interest application cancelled.");
        }
    }

    // ─── STATISTICS ───────────────────────────────────────
    private static void bankStatistics() {
        System.out.println("\n── Bank Statistics ──");
        System.out.println("Bank Name        : " + bank.getBankName());
        System.out.println("Total Accounts   : " + bank.getTotalAccounts());
        System.out.printf ("Total Deposits   : ₹%.2f%n", bank.getTotalDeposits());
        List<Account> low = bank.getLowBalanceAccounts(1000);
        System.out.println("Low Balance (<₹1000) Accounts: " + low.size());
        if (!low.isEmpty()) {
            System.out.println("  These accounts need attention:");
            for (Account a : low) {
                System.out.println("  → " + a.getAccountNumber() +
                        " | " + a.getAccountHolder() +
                        " | ₹" + a.getBalance());
            }
        }
    }

    // ─── CLOSE ACCOUNT ────────────────────────────────────
    private static void closeAccount() {
        String accNo = getStringInput("\nEnter Account Number to Close: ");
        try {
            Account account = bank.getAccount(accNo);
            System.out.println("Account: " + account.getAccountHolder() +
                    " | Balance: ₹" + account.getBalance());
            String confirm = getStringInput("Are you sure you want to close this account? (yes/no): ");
            if (confirm.equalsIgnoreCase("yes")) {
                bank.closeAccount(accNo);
                System.out.println("✅ Account closed successfully.");
            } else {
                System.out.println("⚠️  Account closure cancelled.");
            }
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── TABLE HELPERS ────────────────────────────────────
    private static void printTableHeader() {
        System.out.println("+──────────────+──────────────────────+─────────────────+──────────────+");
        System.out.println("| Account No   | Holder Name          | Type            | Balance (₹)  |");
        System.out.println("+──────────────+──────────────────────+─────────────────+──────────────+");
    }

    private static void printTableFooter() {
        System.out.println("+──────────────+──────────────────────+─────────────────+──────────────+");
    }

    // ─── INPUT HELPERS ────────────────────────────────────
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount.");
            }
        }
    }
}
