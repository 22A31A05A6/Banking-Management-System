# Banking Management System 🏦

A console-based Java Banking application to manage bank accounts with full transaction support.

## 🛠️ Tech Stack
- **Language:** Java (Core Java)
- **Concepts:** OOP, Collections (HashMap), Stream API, Custom Exceptions, File Handling, Inheritance, Polymorphism

## ✨ Features
- 🏦 Create Savings and Current accounts
- 💰 Deposit and Withdraw money
- 🔄 Fund Transfer between accounts
- 📋 View transaction history
- 🔍 Search accounts by name
- 📊 Sort accounts by balance
- 💹 Apply annual interest to all accounts
- 📈 Bank statistics and low balance alerts
- ❌ Close accounts
- 💾 Persistent data storage (survives program restart)

## 🧩 OOP Concepts Demonstrated

| Concept | Where Used |
|---|---|
| **Abstraction** | `Account` is abstract with `calculateInterest()` as abstract method |
| **Inheritance** | `SavingsAccount` and `CurrentAccount` extend `Account` |
| **Polymorphism** | `calculateInterest()` behaves differently per account type |
| **Encapsulation** | Private fields with getters/setters in `Account` class |
| **Custom Exceptions** | `InsufficientFundsException`, `InvalidAmountException`, `AccountNotFoundException` |

## 📦 Collections & Stream API

| Feature | Implementation |
|---|---|
| **HashMap** | Fast O(1) account lookup by account number |
| **Stream filter()** | Search accounts by name, find low balance accounts |
| **Stream sorted()** | Sort accounts by balance |
| **Stream mapToDouble()** | Calculate total deposits |
| **forEach()** | Apply interest to all accounts |

## 💾 File Handling
- Uses Java **Serialization** (`ObjectOutputStream/ObjectInputStream`)
- Data auto-saved after every operation
- Loads existing data on startup

## 🚀 How to Run

### Compile
```bash
cd src
javac *.java
```

### Run
```bash
java Main
```

## 📁 Project Structure
```
BankingManagementSystem/
│
├── src/
│   ├── Account.java                    # Abstract base class
│   ├── SavingsAccount.java             # Extends Account (4% interest)
│   ├── CurrentAccount.java             # Extends Account (overdraft facility)
│   ├── Bank.java                       # Core logic (HashMap, Streams, File I/O)
│   ├── Main.java                       # Console UI & entry point
│   ├── InsufficientFundsException.java # Custom exception
│   ├── InvalidAmountException.java     # Custom exception
│   └── AccountNotFoundException.java  # Custom exception
│
└── README.md
```

## 📸 Sample Output
```
╔══════════════════════════════════════════════╗
║        BANKING MANAGEMENT SYSTEM v1.0        ║
║     Built with Java | OOP | Collections      ║
╚══════════════════════════════════════════════╝

┌──────────────────────────────────────┐
│             MAIN MENU                │
├──────────────────────────────────────┤
│  1.  Create Account                  │
│  2.  View Account Details            │
│  3.  View All Accounts               │
│  4.  Deposit                         │
│  5.  Withdraw                        │
│  6.  Transfer                        │
│  7.  Transaction History             │
│  8.  Search by Name                  │
│  9.  Accounts Sorted by Balance      │
│  10. Apply Interest to All           │
│  11. Bank Statistics                 │
│  12. Close Account                   │
│  0.  Exit                            │
└──────────────────────────────────────┘
```

## 🎯 Interview Talking Points

- **"Why HashMap over ArrayList?"** → HashMap gives O(1) lookup by account number vs O(n) for ArrayList
- **"How is Polymorphism used?"** → `calculateInterest()` is called on `Account` reference but executes SavingsAccount or CurrentAccount version
- **"How does file handling work?"** → Java Serialization saves entire Bank object to disk, loaded on startup
- **"What are custom exceptions for?"** → Better error messages and separation of error types (insufficient funds vs invalid amount)
- **"How does Stream API help?"** → Clean, readable code for filtering/sorting without manual loops

## 👨‍💻 Author
**Samuel Guttula**
B.Tech Computer Science | Pragati Engineering College
[LinkedIn](https://linkedin.com/in/samuel-guttula) | [GitHub](https://github.com/22A31A05A6) | [LeetCode](https://leetcode.com/22A31A05A6)
